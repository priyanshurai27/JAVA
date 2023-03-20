public String APICall(String url, JSONObject payload, String method, String contentType, boolean flagForPayloy) throws Exception {
		StringBuffer response = new StringBuffer();
		System.out.println("Url : "+url);
		URL obj = new URL(url.replace(" ", "%20"));
		HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
		connection.setDoOutput(true);
		connection.setUseCaches(false);
		connection.setRequestMethod(method);
		connection.setRequestProperty("Accept", "*/*");
		connection.setRequestProperty("Connection", "keep-alive");
		connection.setRequestProperty("Content-Type", contentType);
		//reading payload
		if(flagForPayloy) {
			String params = payload.toString();

			OutputStream os = connection.getOutputStream();
			os.write(params.getBytes());
			os.flush();
			os.close();
		}
		
		System.out.println("Waiting for response...");
		int responseCode = connection.getResponseCode();
		String str = connection.getResponseMessage();
		System.out.println("code and msg :: " + responseCode + " " + str);
		
		if (responseCode == HttpURLConnection.HTTP_OK || responseCode == 200) {
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine).toString();
			}
			System.out.println("success response..." + response);
			in.close();
			} else {
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine).toString();
				}
				System.out.println("failed response..." + response);
				in.close();
		}
		
		return response.toString();
		
	}