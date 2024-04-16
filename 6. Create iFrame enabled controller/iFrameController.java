@GetMapping("/GemReportDetailsData")
	public ResponseEntity<String> getGemReportDetails(HttpServletResponse response) throws IOException {
		response.setHeader("Content-Security-Policy", "frame-ancestors http://localhost:8080");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=GemReport.xlsx");
		String htmlResponse = "<html><body><div><h2 style='color: red'>Excel sheet is created at FTP server</h2></div></body></html>";
		return ResponseEntity.ok().body(htmlResponse);
	}