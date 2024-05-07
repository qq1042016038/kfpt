package com.zdww.cdyfzx.techcenter.wepaas.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.CountDownLatch;

public class XunFeiUtil {
	protected static final Logger log = LoggerFactory.getLogger(XunFeiUtil.class);

	private static String hostUrl = "https://tts-api.xfyun.cn/v2/tts";

	private static String appid = "56b24653";


	private static String apiSecret = "NmM1NWMwNDYxNTY2ZGZiMTk5Y2MwYWU3";


	private static String apiKey = "eea0d26c5b793d8ed242e8cd53441bbd";


	public static final Gson json = new Gson();


	/**
	 * 将文本转换为MP3格语音base64文件
	 *
	 * @param text 要转换的文本（如JSON串）
	 * @return 转换后的base64文件
	 */
	public static String convertText(String text) throws Exception {
		StringBuilder stringBuilder = new StringBuilder();
		CountDownLatch countDownLatch = new CountDownLatch(1);
		// 构建鉴权url
		String authUrl = getAuthUrl(hostUrl, apiKey, apiSecret);
		OkHttpClient client = new OkHttpClient.Builder().build();
		Request request = new Request.Builder().url(authUrl).build();
		List<byte[]> list = new ArrayList<>();
		WebSocket webSocket = client.newWebSocket(request, new WebSocketListener() {
			@Override
			public void onOpen(WebSocket webSocket, Response response) {
				super.onOpen(webSocket, response);
				try {
					System.out.println(response.body().string());
				} catch (IOException e) {
					e.printStackTrace();
				}
				//发送数据
				JsonObject frame = new JsonObject();
				JsonObject business = new JsonObject();
				JsonObject common = new JsonObject();
				JsonObject data = new JsonObject();
				// 填充common
				common.addProperty("app_id", appid);
				//填充business,AUE属性lame是MP3格式，raw是PCM格式
				business.addProperty("aue", "lame");
				business.addProperty("sfl", 1);
				business.addProperty("tte", "UTF8");//小语种必须使用UNICODE编码
				business.addProperty("vcn", "xiaoyan");//到控制台-我的应用-语音合成-添加试用或购买发音人，添加后即显示该发音人参数值，若试用未添加的发音人会报错11200
				business.addProperty("pitch", 50);
				business.addProperty("speed", 50);
				//填充data
				data.addProperty("status", 2);//固定位2
				try {
					data.addProperty("text", Base64.getEncoder().encodeToString(text.getBytes("utf8")));
					//使用小语种须使用下面的代码，此处的unicode指的是 utf16小端的编码方式，即"UTF-16LE"”
					//data.addProperty("text", Base64.getEncoder().encodeToString(text.getBytes("UTF-16LE")));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				//填充frame
				frame.add("common", common);
				frame.add("business", business);
				frame.add("data", data);
				webSocket.send(frame.toString());
			}

			@Override
			public void onMessage(WebSocket webSocket, String text) {
				super.onMessage(webSocket, text);
				//处理返回数据
				ResponseData resp = null;
				try {
					resp = json.fromJson(text, ResponseData.class);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (resp != null) {
					if (resp.getCode() != 0) {
						return;
					}
					if (resp.getData() != null) {
						String result = resp.getData().audio;
						byte[] audio = Base64.getDecoder().decode(result);
						list.add(audio);
						// 说明数据全部返回完毕，可以关闭连接，释放资源
						if (resp.getData().status == 2) {
							String is = base64Concat(list);
							stringBuilder.append(is);
							countDownLatch.countDown();
							webSocket.close(1000, "");
						}
					}
				}
			}

			@Override
			public void onMessage(WebSocket webSocket, ByteString bytes) {
				super.onMessage(webSocket, bytes);
			}

			@Override
			public void onClosing(WebSocket webSocket, int code, String reason) {
				super.onClosing(webSocket, code, reason);
				System.out.println("socket closing");
			}

			@Override
			public void onClosed(WebSocket webSocket, int code, String reason) {
				super.onClosed(webSocket, code, reason);
				System.out.println("socket closed");
			}

			@Override
			public void onFailure(WebSocket webSocket, Throwable t, Response response) {
				super.onFailure(webSocket, t, response);
			}
		});
		countDownLatch.await();
		return stringBuilder.toString();
	}

	/**
	 * * base64拼接
	 */
	static String base64Concat(List<byte[]> list) {
		int length = 0;
		for (byte[] b : list) {
			length += b.length;
		}
		int len = 0;
		byte[] retByte = new byte[length];
		for (byte[] b : list) {
			retByte = concat(len, retByte, b);
			len += b.length;
		}
		return cn.hutool.core.codec.Base64.encode(retByte);
	}

	static byte[] concat(int len, byte[] a, byte[] b) {
		for (int i = 0; i < b.length; i++) {
			a[len] = b[i];
			len++;
		}
		return a;
	}

	/**
	 * * 获取权限地址
	 * *
	 * * @param hostUrl
	 * * @param apiKey
	 * * @param apiSecret
	 * * @return
	 */
	private static String getAuthUrl(String hostUrl, String apiKey, String apiSecret) throws Exception {
		URL url = new URL(hostUrl);
		SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
		format.setTimeZone(TimeZone.getTimeZone("GMT"));
		String date = format.format(new Date());
		StringBuilder builder = new StringBuilder("host: ").append(url.getHost()).append("\n").
				append("date: ").append(date).append("\n").
				append("GET ").append(url.getPath()).append(" HTTP/1.1");
		Charset charset = Charset.forName("UTF-8");
		Mac mac = Mac.getInstance("hmacsha256");
		SecretKeySpec spec = new SecretKeySpec(apiSecret.getBytes(charset), "hmacsha256");
		mac.init(spec);
		byte[] hexDigits = mac.doFinal(builder.toString().getBytes(charset));
		String sha = Base64.getEncoder().encodeToString(hexDigits);
		String authorization = String.format("hmac username=\"%s\", algorithm=\"%s\", headers=\"%s\", "
				+ "signature=\"%s\"", apiKey, "hmac-sha256", "host date request-line", sha);
		HttpUrl httpUrl = HttpUrl.parse("https://" + url.getHost() + url.getPath()).newBuilder().
				addQueryParameter("authorization",
						Base64.getEncoder().encodeToString(authorization.getBytes(charset))).
				addQueryParameter("date", date).
				addQueryParameter("host", url.getHost()).
				build();
		return httpUrl.toString();
	}


	public static class ResponseData {
		private int code;
		private String message;
		private String sid;
		private Data data;

		public int getCode() {
			return code;
		}

		public String getMessage() {
			return this.message;
		}

		public String getSid() {
			return sid;
		}

		public Data getData() {
			return data;
		}
	}

	private static class Data {
		//标志音频是否返回结束  status=1，表示后续还有音频返回，status=2表示所有的音频已经返回
		private int status;
		//返回的音频，base64 编码
		private String audio;
		// 合成进度
		private String ced;
	}

	public static void main(String[] args) throws Exception {
		String s = XunFeiUtil.convertText("请你留言！！！！");
		System.out.println(s);
		byte[] mp3 = Base64.getDecoder().decode(s);
		System.out.println(mp3);
		File file = new File("testxunfei.mp3");
		FileOutputStream out = new FileOutputStream(file);
		out.write(mp3);
		out.flush();
		out.close();
	}
}

