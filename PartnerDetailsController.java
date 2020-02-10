package com.unimoni.LoadBalancer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Date;

import com.unimoni.LoadBalancer.model.PartnerDetails;
import com.unimoni.LoadBalancer.services.PartnerDetailsService;;

@RestController
public class PartnerDetailsController {
    
	@Autowired
	PartnerDetailsService partnerDetailsService;
	
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/partnerDetails")
	public ResponseEntity<List<PartnerDetails>> getpartnerDetails() {
		
		System.out.println("--getpartnerDetails--");
		List<PartnerDetails> list = partnerDetailsService.getpartnerDetails();
		System.out.println("--getpartnerDetails-list-" + list.size());
		return ResponseEntity.status(200).body(list);

	}
	
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/partnerDetails/getTrendAnalysisReport/{partnerCode}/{requestDate}")
	public ResponseEntity<List<PartnerDetails>> getTrendAnalysisReport(@PathVariable("partnerCode") String partnerCode,@PathVariable("requestDate") Date requestDate) {
		
		System.out.println("--getTrendAnalysisReport--");
		List<PartnerDetails> list = partnerDetailsService.getTrendAnalysisReport(partnerCode,requestDate);
		System.out.println("--getTrendAnalysisReport-list-" + list.size());
		return ResponseEntity.status(200).body(list);
	}
	
	
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/partnerDetails/getTrendAnalysisReportOne/{partnerCode}/{requestDate}")
	//public ResponseEntity<PartnerDetails> getTrendAnalysisReport(@PathVariable("partnerCode") String partnerCode) {
	public ResponseEntity<List<PartnerDetails>>getTrendAnalysisReportOne(@PathVariable("partnerCode") String partnerCode, @PathVariable("requestDate") Date requestDate) {
		
		System.out.println("--getTrendAnalysisReportOne--" + partnerCode); 
		System.out.println("--getTrendAnalysisReportOne-requestDate>>-" + requestDate); 
		List<PartnerDetails> list = partnerDetailsService.getTrendAnalysisReportOne(partnerCode,requestDate);
		//PartnerDetails partnerDetails = partnerDetailsService.getTrendAnalysisReportOne(partnerCode);
		System.out.println("--getTrendAnalysisReportOne-list-" + list);
		return ResponseEntity.status(200).body(list);
	}
	
	
}

