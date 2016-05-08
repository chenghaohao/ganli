package com.ganli.common.util;


import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.Args;
import org.apache.http.util.CharArrayBuffer;

import java.io.*;
import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.*;

/**
 * Copyright (c) 2015, 北京卡拉卡尔科技股份有限公司 All rights reserved.
 *
 * @Description: Http请求文本类型工具类，获取返回的字符串结果
 * @since
 * @author xiaolong.li@karakal.com.cn (李小龙)   
 * @date 2015年7月21日 上午11:03:31 
 * @version 1.0
 */
public class HttpClientUtil {
	public static Log log = LogFactory.getLog(HttpClientUtil.class);
	public static RequestConfig defaultRequestConfig = RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(5000).setSocketTimeout(15000).build();
	public static PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
	public static CloseableHttpClient httpClient;

	public HttpClientUtil() {
	}

	public static void closeHttpClient() {
		IOUtils.closeQuietly(httpClient);
	}

	public static HttpClientUtil.Response execute(HttpRequestBase request, Map<String, String> headers, RequestConfig requestConfig) throws ClientProtocolException, IOException {
		request.setConfig(requestConfig);
		addHeaders(request, headers);
		HttpClientUtil.Response res = (HttpClientUtil.Response)httpClient.execute(request, new ResponseHandler() {
			public HttpClientUtil.Response handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
				StatusLine statusLine = response.getStatusLine();
				HttpEntity entity = response.getEntity();
				if(statusLine.getStatusCode() != 200) {
					throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
				} else {
					HttpClientUtil.Response res = new HttpClientUtil.Response();
					res.setHeaders(response.getAllHeaders());
					if(entity == null) {
						throw new ClientProtocolException("Response contains no content");
					} else {
						ContentType contentType = ContentType.get(entity);
						res.setContentType(contentType);
						InputStream is = entity.getContent();
						org.apache.commons.io.output.ByteArrayOutputStream bos = new org.apache.commons.io.output.ByteArrayOutputStream();

						try {
							byte[] buf = new byte[2048];
							boolean len = false;

							int len1;
							while((len1 = is.read(buf)) != -1) {
								bos.write(buf, 0, len1);
							}

							res.setContent(bos.toByteArray());
							HttpClientUtil.Response var10 = res;
							return var10;
						} finally {
							IOUtils.closeQuietly(is);
							IOUtils.closeQuietly(bos);
						}
					}
				}
			}
		});
		return res;
	}

	public static HttpClientUtil.Response executeHeader(HttpRequestBase request, Map<String, String> headers, RequestConfig requestConfig) throws ClientProtocolException, IOException {
		request.setConfig(requestConfig);
		addHeaders(request, headers);
		HttpClientUtil.Response res = (HttpClientUtil.Response)httpClient.execute(request, new ResponseHandler() {
			public HttpClientUtil.Response handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
				StatusLine statusLine = response.getStatusLine();
				if(statusLine.getStatusCode() != 200) {
					throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
				} else {
					HttpClientUtil.Response res = new HttpClientUtil.Response();
					res.setHeaders(response.getAllHeaders());
					return res;
				}
			}
		});
		return res;
	}

	public static HttpClientUtil.Response get(String url, Map<String, String> headers, RequestConfig requestConfig) throws ClientProtocolException, IOException, URISyntaxException {
		HttpGet get = new HttpGet((new URIBuilder(url)).build());
		return execute(get, headers, requestConfig);
	}

	public static HttpClientUtil.Response get(String url, Map<String, String> headers) throws ClientProtocolException, IOException, URISyntaxException {
		return get(url, headers, defaultRequestConfig);
	}

	public static HttpClientUtil.Response get(String url) throws ClientProtocolException, IOException, URISyntaxException {
		return get(url, (Map)null, defaultRequestConfig);
	}

	public static HttpClientUtil.Response getHeader(String url, Map<String, String> headers, RequestConfig requestConfig) throws ClientProtocolException, IOException, URISyntaxException {
		HttpGet get = new HttpGet((new URIBuilder(url)).build());
		return executeHeader(get, headers, requestConfig);
	}

	public static HttpClientUtil.Response getHeader(String url, Map<String, String> headers) throws ClientProtocolException, IOException, URISyntaxException {
		return getHeader(url, headers, defaultRequestConfig);
	}

	public static HttpClientUtil.Response getHeader(String url) throws ClientProtocolException, IOException, URISyntaxException {
		return get(url, (Map)null, defaultRequestConfig);
	}

	public static HttpClientUtil.Response post(String url, Map<String, String> params, Map<String, String> headers, RequestConfig requestConfig) throws ClientProtocolException, IOException, URISyntaxException {
		HttpPost post = new HttpPost((new URIBuilder(url)).build());
		ArrayList formParams = new ArrayList();
		if(params != null) {
			Iterator uefEntity = params.keySet().iterator();

			while(uefEntity.hasNext()) {
				String key = (String)uefEntity.next();
				if(params.get(key) != null) {
					formParams.add(new BasicNameValuePair(key, (String)params.get(key)));
				}
			}
		}

		UrlEncodedFormEntity uefEntity1 = new UrlEncodedFormEntity(formParams, "UTF-8");
		post.setEntity(uefEntity1);
		return execute(post, headers, requestConfig);
	}

	public static HttpClientUtil.Response post(String url, Map<String, String> params, Map<String, String> headers) throws ClientProtocolException, IOException, URISyntaxException {
		return post(url, params, headers, defaultRequestConfig);
	}

	public static HttpClientUtil.Response post(String url, Map<String, String> params) throws ClientProtocolException, IOException, URISyntaxException {
		return post(url, params, (Map)null, defaultRequestConfig);
	}

	public static HttpClientUtil.Response upload(String url, String fileFieldName, File file, Map<String, String> params, Map<String, String> headers, RequestConfig requestConfig) throws ClientProtocolException, IOException {
		HttpPost post = new HttpPost(url);
		post.setConfig(defaultRequestConfig);
		MultipartEntityBuilder builder = MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE).addBinaryBody(fileFieldName, file);
		if(params != null) {
			Iterator httpEntity = params.keySet().iterator();

			while(httpEntity.hasNext()) {
				String key = (String)httpEntity.next();
				builder.addTextBody(key, (String)params.get(key));
			}
		}

		HttpEntity httpEntity1 = builder.build();
		post.setEntity(httpEntity1);
		return execute(post, headers, requestConfig);
	}

	public static HttpClientUtil.Response upload(String url, String fileFieldName, File file, Map<String, String> params, Map<String, String> headers) throws ClientProtocolException, IOException {
		return upload(url, fileFieldName, file, params, headers, defaultRequestConfig);
	}

	public static HttpClientUtil.Response upload(String url, String fileFieldName, File file, Map<String, String> params) throws ClientProtocolException, IOException {
		return upload(url, fileFieldName, file, params, (Map) null, defaultRequestConfig);
	}

	public static HttpClientUtil.Response upload(String url, String fileFieldName, File file) throws ClientProtocolException, IOException {
		return upload(url, fileFieldName, file, (Map)null, (Map)null, defaultRequestConfig);
	}

	public static boolean download() {
		return false;
	}

	public static void addHeaders(HttpRequestBase request, Map<String, String> headers) {
		if(headers != null) {
			Iterator i$ = headers.keySet().iterator();

			while(i$.hasNext()) {
				String key = (String)i$.next();
				request.addHeader(key, (String)headers.get(key));
			}
		}

	}

	public static void main(String[] args) {
		try {
			HttpClientUtil.Response e = getHeader("http://img.karakal.com.cn:4869/592546ebdc0e055600a7e4f1a91de33e?w=300&h=80");
			System.out.println(e.getHeader("Content-Length"));
		} catch (ClientProtocolException var2) {
			var2.printStackTrace();
		} catch (IOException var3) {
			var3.printStackTrace();
		} catch (URISyntaxException var4) {
			var4.printStackTrace();
		}

	}

	static {
		connectionManager.setMaxTotal(200);
		connectionManager.setDefaultMaxPerRoute(20);
		httpClient = HttpClients.custom().setConnectionManager(connectionManager).build();
	}

	public static class Response {
		private byte[] content;
		private ContentType contentType;
		private Header[] headers;

		public Map<String, String> getHeaders(List<String> keys) {
			HashMap map = new HashMap();
			Header[] arr$ = this.headers;
			int len$ = arr$.length;

			for(int i$ = 0; i$ < len$; ++i$) {
				Header h = arr$[i$];
				if(keys.contains(h.getName())) {
					map.put(h.getName(), h.getValue());
				}
			}

			return map;
		}

		public String getHeader(String key) {
			Header[] arr$ = this.headers;
			int len$ = arr$.length;

			for(int i$ = 0; i$ < len$; ++i$) {
				Header h = arr$[i$];
				if(key.equals(h.getName())) {
					return h.getValue();
				}
			}

			return null;
		}

		public Map<String, String> getAllHeaders() {
			HashMap map = new HashMap();
			Header[] arr$ = this.headers;
			int len$ = arr$.length;

			for(int i$ = 0; i$ < len$; ++i$) {
				Header h = arr$[i$];
				map.put(h.getName(), h.getValue());
			}

			return map;
		}

		public String toString(String encoding) {
			Charset charset = Charset.forName(encoding);
			return new String(this.content, charset);
		}

		public String toContentTypeString(String defEncoding) {
			Charset charset = null;
			charset = this.contentType.getCharset();
			if(charset == null) {
				charset = Charset.forName(defEncoding);
			}

			return new String(this.content, charset);
		}

		public String toString() {
			return this.toContentTypeString("UTF-8");
		}

		public Response() {
		}

		public Response(byte[] content, ContentType contentType) {
			this.content = content;
			this.contentType = contentType;
		}

		public byte[] getContent() {
			return this.content;
		}

		public void setContent(byte[] content) {
			this.content = content;
		}

		public ContentType getContentType() {
			return this.contentType;
		}

		public void setContentType(ContentType contentType) {
			this.contentType = contentType;
		}

		public final Header[] getHeaders() {
			return this.headers;
		}

		public final void setHeaders(Header[] headers) {
			this.headers = headers;
		}
	}
}
