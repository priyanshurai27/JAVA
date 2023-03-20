	//Read CSV File without header ( file doesn't contains header row. header comes from external resources)
	public List<Map<String, String>> readCSVDataWithoutHeader(InputStream inputStream , List header) throws Exception {
		//change separator as per requirement
		CSVParser parser = new CSVParserBuilder().withSeparator('|').build();
		
		//BufferedReader csvReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
		
		CSVReader csvReader = new CSVReaderBuilder(new InputStreamReader(inputStream, "UTF-8"))
                .withCSVParser(parser)
                .withSkipLines(1)
                .build();
		
		List<String[]> allData = csvReader.readAll();
		
		//Read the CSV as List of Maps where each Map represents row data
		List<Map<String, String>> rows = new ArrayList<Map<String, String>>();
		int k=1;
		for (String[] row : allData) {
			Map<String, String> csvFileData = new LinkedHashMap<String, String>();
			for(int i=0; i < header.size();i++) {
				csvFileData.put(header.get(i).toString(), row[i]==null? "" : row[i]);
			}
			//add Row map to list of rows
			rows.add(csvFileData);
        }
		return rows;
	}
	
	//Read CSV File  with header
	public List<Map<String, String>> readCSVDataWithHeader(InputStream inputStream, List<String> requiredHeader) throws Exception{
		
		// change char quote, column separator and line separator as per requrements
		final CsvPreference PIPE_DELIMITED = new CsvPreference.Builder('"', '|', "\n").build();
		//Read the CSV as List of Maps where each Map represents row data
	      List<Map<String, String>> rows = new ArrayList<Map<String, String>>(); 
	      
		try {
			CsvListReader csvReader = new CsvListReader(new InputStreamReader(inputStream, "UTF-8"),PIPE_DELIMITED);
			//Read CSV Header
		    List<String> header = new ArrayList<String>(csvReader.read());		
		    System.out.println("header : "+header);
		    if(!(requiredHeader.isEmpty()||requiredHeader.equals(null))) {
		    	if(!(header.size() == requiredHeader.size())) {
		    		throw new Exception("incorrect header");
		    	}
		    	String headerNotAvailable = "";
				//file have fixed header this loop is used for validate the header (required header define outside .... it will List<String> Format)
		    	for(int i=0;i<requiredHeader.size();i++) {
		    		if(!requiredHeader.get(i).toString().equals(header.get(i).toString())) {
		    			System.out.println(requiredHeader.get(i).toString());
			    		headerNotAvailable = headerNotAvailable+" "+requiredHeader.get(i).toString();
			    	}
		    			
		    	}
		    	else if(!requiredHeader.get(i).toString().equals(header.get(i).toString())) {
		    			headerNotAvailable = headerNotAvailable+" "+requiredHeader.get(i).toString();
		    	}
		    }
		    if(!(headerNotAvailable.length() == 0 || headerNotAvailable.equalsIgnoreCase("")||headerNotAvailable == "")) {
		    	throw new Exception("incorrect header Sequence or name : "+headerNotAvailable);
		    }
		    
		    List<String> rowAsTokens;		      
		    int k=1;
		      
		    while ((rowAsTokens = csvReader.read()) != null) {
		    	  
		        //Create Map for each row in CSV
				try {
					if(header.size() == rowAsTokens.size()) {
						Map<String, String> row = new LinkedHashMap<String, String>();
				    	if(!(header.contains("row_id")||header.contains("row_Id"))) {
				    	    row.put("row_id", String.valueOf(k));
				    		k++;
				    	}
				        for (int i = 0 ; i < header.size() ; i++) {
				        	if(rowAsTokens.get(i) == null||rowAsTokens.get(i).toString() == null || rowAsTokens.get(i).toString().equals(null)||rowAsTokens.get(i).equals(null)) {
				        		row.put(header.get(i), "");
				        	}
				        	else {
				        		
				        		row.put(header.get(i), rowAsTokens.get(i).toString().trim());
				        	}
							//add Row map to list of rows
							rows.add(row);
						}
					}
			    	else {
						throw new Exception("incorrect delimiter in row number"+k);
			    	}
		    	}catch (Exception e) {
					// TODO: handle exception
		    		System.out.println(e.getMessage());
		    		e.printStackTrace();
		    	}
			    
		    }
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
  		  	
  		  	e.printStackTrace();
		}
		
	    return rows;
	}