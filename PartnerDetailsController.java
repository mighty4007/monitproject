	@Query(value = "SELECT * FROM partner_details u WHERE u.partner_code = ?1 and u.request_date >= ?2",nativeQuery=true)
	//PartnerDetails getTrendAnalysisReportOne(String partnerCode);
	//public Optional<PartnerDetails> getTrendAnalysisReportOne(String userName);
	public List<PartnerDetails> getTrendAnalysisReportOne(String partnerCode,Date requestDate);
	
