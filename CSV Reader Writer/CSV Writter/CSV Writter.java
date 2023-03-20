	//creating csv
	public InputStream csvWriter(List<Map<String, String>> listOfMap) throws Exception {
		CsvSchema schema = null;
		CsvSchema.Builder schemaBuilder = CsvSchema.builder();
		if (listOfMap != null && !listOfMap.isEmpty()) {
			for (String col : listOfMap.get(0).keySet()) {
				schemaBuilder.addColumn(col);
			}
			// change line separator and column separator as per requirement.....
			schema = schemaBuilder.build().withLineSeparator("\r\n").withColumnSeparator('|').withoutQuoteChar().withHeader();
		}
		CsvMapper mapper = new CsvMapper();
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try (Writer writer = new OutputStreamWriter(byteArrayOutputStream)) {
			mapper.writer(schema).writeValues(writer).writeAll(listOfMap).close();
		} 
		//code for encoded data...
		//String base64CodeString = Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
		byte[] filedata = byteArrayOutputStream.toByteArray();
		        
		ObjectMetadata metaData = new ObjectMetadata();
		metaData.setContentLength(filedata.length);
		        
		InputStream is = new ByteArrayInputStream(filedata);
		return is;
	}