package com.splunkstart.android.http;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.params.ConnManagerPNames;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.LayeredSocketFactory;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;

import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.util.Log;

public class WebClientServerRequest {

	public static GetResponseStatusCode m_GetResponseStatusCode;

	public static JSONObject Post(String url, HttpEntity entity) {
		try {
			return Execute(url, entity);
		} catch (Exception ex) {

		}
		return null;
	}

	public static JSONObject Get(String url, HttpEntity entity)
			throws Exception {
		try {
			return Execute(url, entity);
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}

		// return null;
	}

	/*
	 * public static JSONObject Get(String url, RequestParams params) throws
	 * Exception { try { return Execute(getUrlWithQueryString(url, params),
	 * null); } catch (Exception ex) { throw new Exception(ex.getMessage()); }
	 * 
	 * // return null; }
	 */

	protected static JSONObject Execute(String url, HttpEntity entity)
			throws Exception {
		String responseBody;

		HttpResponse response = null;

		try {

			if (!url.endsWith("?")) {
				url += "?";
			}

			HttpClient httpclient = httpsClient();

			HttpPost httppost = new HttpPost(url);

			if (entity != null) {
				httppost.setEntity(entity);

			}
			httppost.setHeader("Accept", "application/json");
			httppost.setHeader("Content-type", "application/json");
			response = httpclient.execute(httppost);

		} catch (Exception e) {

			Log.e("log_tag", "Error in http connection " + e.toString());

		}

		try {

			entity = null;
			HttpEntity temp = response.getEntity();
			if (temp != null) {
				entity = new BufferedHttpEntity(temp);
			}

			responseBody = EntityUtils.toString(entity);

		} catch (IOException e) {
			throw new Exception(e);
		}

		StatusLine status = response.getStatusLine();
		if (status.getStatusCode() >= 300) {
			throw new HttpResponseException(status.getStatusCode(),
					status.getReasonPhrase());
		}

		Object jsonResponse;
		try {
			jsonResponse = parseResponse(responseBody);
			// if(jsonResponse instanceof JSONObject) {
			// return (JSONObject)jsonResponse;
			// }
		} catch (JSONException e) {
			throw new Exception(e.getMessage());
		}

		return (JSONObject) jsonResponse;

	}

	protected static Object parseResponse(String responseBody)
			throws JSONException {
		return new JSONTokener(responseBody).nextValue();
	}

	/*
	 * private static String getUrlWithQueryString(String url, RequestParams
	 * params) { if (params != null) { String paramString =
	 * params.getParamString(); url += "?&" + paramString; }
	 * 
	 * return url; }
	 */

	/**
	 * Socket Connetcion
	 * 
	 */

	private static DefaultHttpClient httpsClient() {
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		schemeRegistry.register(new Scheme("https", new EasySSLSocketFactory(),
				443));

		HttpParams httpParams = new BasicHttpParams();
		httpParams.setParameter(ConnManagerPNames.MAX_TOTAL_CONNECTIONS, 30);
		httpParams.setParameter(ConnManagerPNames.MAX_CONNECTIONS_PER_ROUTE,
				new ConnPerRouteBean(30));
		httpParams.setParameter(HttpProtocolParams.USE_EXPECT_CONTINUE, false);
		HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);

		ClientConnectionManager cm = new SingleClientConnManager(httpParams,
				schemeRegistry);
		return new DefaultHttpClient(cm, httpParams);
	}

	private static class EasySSLSocketFactory implements LayeredSocketFactory {
		private SSLContext sslcontext = null;

		private SSLContext createEasySSLContext() throws IOException {
			try {
				SSLContext context = SSLContext.getInstance("TLS");
				context.init(null,
						new TrustManager[] { new EasyX509TrustManager(null) },
						null);
				return context;
			} catch (Exception e) {
				throw new IOException(e.getMessage());
			}
		}

		private SSLContext getSSLContext() throws IOException {
			if (this.sslcontext == null) {
				this.sslcontext = createEasySSLContext();
			}
			return this.sslcontext;
		}

		/**
		 * @see org.apache.http.conn.scheme.SocketFactory#connectSocket(java.net.Socket,
		 *      java.lang.String, int, java.net.InetAddress, int,
		 *      org.apache.http.params.HttpParams)
		 */
		public Socket connectSocket(Socket sock, String host, int port,
				InetAddress localAddress, int localPort, HttpParams params)
				throws IOException, UnknownHostException,
				ConnectTimeoutException {
			int connTimeout = HttpConnectionParams.getConnectionTimeout(params);
			int soTimeout = HttpConnectionParams.getSoTimeout(params);

			InetSocketAddress remoteAddress = new InetSocketAddress(host, port);
			SSLSocket sslsock = (SSLSocket) ((sock != null) ? sock
					: createSocket());

			if ((localAddress != null) || (localPort > 0)) {
				// we need to bind explicitly
				if (localPort < 0) {
					localPort = 0; // indicates "any"
				}
				InetSocketAddress isa = new InetSocketAddress(localAddress,
						localPort);
				sslsock.bind(isa);
			}

			sslsock.connect(remoteAddress, connTimeout);
			sslsock.setSoTimeout(soTimeout);
			return sslsock;

		}

		/**
		 * @see org.apache.http.conn.scheme.SocketFactory#createSocket()
		 */
		public Socket createSocket() throws IOException {
			return getSSLContext().getSocketFactory().createSocket();
		}

		/**
		 * @see org.apache.http.conn.scheme.SocketFactory#isSecure(java.net.Socket)
		 */
		public boolean isSecure(Socket socket) throws IllegalArgumentException {
			return true;
		}

		/**
		 * @see org.apache.http.conn.scheme.LayeredSocketFactory#createSocket(java.net.Socket,
		 *      java.lang.String, int, boolean)
		 */
		public Socket createSocket(Socket socket, String host, int port,
				boolean autoClose) throws IOException, UnknownHostException {
			// return getSSLContext().getSocketFactory().createSocket(socket,
			// host, port, autoClose);
			return getSSLContext().getSocketFactory().createSocket(socket,
					host, port, autoClose);
		}

		// -------------------------------------------------------------------
		// javadoc in org.apache.http.conn.scheme.SocketFactory says :
		// Both Object.equals() and Object.hashCode() must be overridden
		// for the correct operation of some connection managers
		// -------------------------------------------------------------------

		public boolean equals(Object obj) {
			return ((obj != null) && obj.getClass().equals(
					EasySSLSocketFactory.class));
		}

		public int hashCode() {
			return EasySSLSocketFactory.class.hashCode();
		}

	}

	private static class EasyX509TrustManager implements X509TrustManager {
		private X509TrustManager standardTrustManager = null;

		/**
		 * Constructor for EasyX509TrustManager.
		 */
		public EasyX509TrustManager(KeyStore keystore)
				throws NoSuchAlgorithmException, KeyStoreException {
			super();
			TrustManagerFactory factory = TrustManagerFactory
					.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			factory.init(keystore);
			TrustManager[] trustmanagers = factory.getTrustManagers();
			if (trustmanagers.length == 0) {
				throw new NoSuchAlgorithmException("no trust manager found");
			}
			this.standardTrustManager = (X509TrustManager) trustmanagers[0];
		}

		/**
		 * @see 
		 *      javax.net.ssl.X509TrustManager#checkClientTrusted(X509Certificate
		 *      [],String authType)
		 */
		public void checkClientTrusted(X509Certificate[] certificates,
				String authType) throws CertificateException {
			standardTrustManager.checkClientTrusted(certificates, authType);
		}

		/**
		 * @see 
		 *      javax.net.ssl.X509TrustManager#checkServerTrusted(X509Certificate
		 *      [],String authType)
		 */
		public void checkServerTrusted(X509Certificate[] certificates,
				String authType) throws CertificateException {
			if ((certificates != null) && (certificates.length == 1)) {
				certificates[0].checkValidity();
			} else {
				standardTrustManager.checkServerTrusted(certificates, authType);
			}
		}

		/**
		 * @see javax.net.ssl.X509TrustManager#getAcceptedIssuers()
		 */
		public X509Certificate[] getAcceptedIssuers() {
			return this.standardTrustManager.getAcceptedIssuers();
		}

	}

	public interface GetResponseStatusCode {
		void GetStatusCode(int statusCode);
	}

}
