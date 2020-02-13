import { Typography } from "@material-ui/core";
import Grid from '@material-ui/core/Grid';
import Axios from 'axios';
import React, { Component } from "react";
import { Accordion, AccordionItem, AccordionItemButton, AccordionItemHeading, AccordionItemPanel } from 'react-accessible-accordion';
import 'react-accessible-accordion/dist/fancy-example.css';
import { HorizontalBar } from 'react-chartjs-2';
import Widget from "../../components/Widget";






export default class Dashboard extends Component {

  constructor(){
    super();
    this.state = {
      count:'',
      top10:{labels:[],datasets:[]},
      data:{data:{openPrice:[], highPrice:[], lowPrice:[], ltp:[], previousPrice:[], netPrice:[], tradedQuantity:[], turnoverInLakhs:[], lastCorpAnnouncementDate:[],lastCorpAnnouncement:[]}},
      cardData:{open:[], high:[], low:[], last:[], previousClose:[], percChange:[], yearHigh:[], yearLow:[], indexOrder:[]},
      cardData1:{agentCode:[], noOfHits:[], noOfSendTxnHits:[], noOfCancelTxnHits:[],noOfEnquiryTxnHits:[],noOfSolrSearchHits:[]
               ,noOfRatesHits:[]}
    }
  }

  storeData(temp){


    //var len = Object.keys(temp).length
    const agentCode=[];
    const noOfHits=[];
    const noOfSendTxnHits=[];
    const noOfCancelTxnHits=[];

    //for(var i=0; i<10; i++)
    for (const key in temp) 
    {


      agentCode[key]=temp.data[key].agentCode
      noOfSendTxnHits[key]=temp.data[key].noOfSendTxnHits
      noOfCancelTxnHits[key]=temp.data[key].noOfCancelTxnHits
      noOfHits[key]=temp.data[key].noOfHits


    }
    this.setState({
      cardData: {
        agentCode: agentCode,
        noOfHits: noOfHits,
        noOfSendTxnHits: noOfSendTxnHits,
        noOfCancelTxnHits: noOfCancelTxnHits
      }})
  }
  
  componentWillMount(){
    document.title = 'Load Monitoring'
    this.fetchStocks();
  }


  fetchStocks() {
    //Axios.get('https://nseindia.com/live_market/dynaContent/live_watch/stock_watch/liveIndexWatchData.json').then(res => { 
      Axios.defaults.baseURL = 'http://myurl';
      Axios.defaults.headers.post['Content-Type'] ='application/json;charset=utf-8';
      Axios.defaults.headers.post['Access-Control-Allow-Origin'] = '*';
    //Axios.get('http://localhost:8080/').then(res => { 

    
      Axios.get('http://localhost:8888/getAllEntityData/').then(entity_res => {
        
        const entity_labels= [];
        const entity_methods= [];

        console.log("in did getAllEntityData STATE",entity_res)

        const response =entity_res.data;
        const hitDataGroupBy_Entity =response.hitDataGroupBy_Entity;
        const hitDataGroupBy_Entity_Method =response.hitDataGroupBy_Entity_Method;
        const chartData =response.chartData;
       

        console.log("hitDataGroupBy_Entity:",hitDataGroupBy_Entity)
        console.log("hitDataGroupBy_Entity_Method:",hitDataGroupBy_Entity_Method)
        console.log("chartData:",chartData)

        for (const key in hitDataGroupBy_Entity) {
          entity_labels[key]=hitDataGroupBy_Entity[key].entityCode
        }

   
        console.log("entity_labels:",entity_labels)

        this.setState({
          top10:{
            labels: entity_labels,
            datasets:chartData
          }        
        })

        }
      )


    Axios.get('http://localhost:8007/partnerHits/').then(res => { 
      console.log("in did MUICustomDataTable STATE",res.data)
      this.count = Object.keys(res.data).length
      console.log("in did THIS DATA COUNT",this.count)

      //this.data = this.dumpComma(res.data);
      this.data = res.data;
      //this.fcolor(this.data);
      console.log("in did THIS DATA STATE",this.data)

      

     // this.storeData(this.data);
      const labels= [];
      const sendHits=[];
      const cancelHits=[];
      const noOfHits=[];
      const enquiryHits=[];
      const solrHits=[];
      const ratesHits=[];
      


      const otherMethodHits=[];

      for (const key in res.data) {

        
        labels[key]=res.data[key].agentCode
        sendHits[key]=res.data[key].noOfSendTxnHits
        cancelHits[key]=res.data[key].noOfCancelTxnHits
        noOfHits[key]=res.data[key].noOfHits

        enquiryHits[key]=res.data[key].noOfEnquiryTxnHits
        solrHits[key]=res.data[key].noOfSolrSearchHits
        ratesHits[key]=res.data[key].noOfRatesHits
      }

      console.log("log_Key:",labels)
      console.log("log_Key:",sendHits)

      this.setState({
        cardData1: {
          agentCode: labels,
          noOfHits: noOfHits,
          noOfSendTxnHits: sendHits,
          noOfCancelTxnHits: cancelHits,
          noOfEnquiryTxnHits:enquiryHits,
          noOfSolrSearchHits:solrHits,
          noOfRatesHits:ratesHits
        }})

        const dataforbar =[
          {
            label:'Txn Hits',
            backgroundColor:"red",
            data: cancelHits
          },
          {
            label:'Send Txn Hits',
            backgroundColor:"blue",
            data: sendHits
          },
          {
            label:'Total No of Hits',
            backgroundColor:"#FF5733",
            data: noOfHits
          }
        ]

        console.log("data for bar:",dataforbar)

      // this.setState({
      //   top10:{
      //     labels: labels,
      //     datasets:dataforbar
      //   }        
      // })
    }

    
      )
  }


   render(){
      var chart= this.state.top10
      var indents = [];
      console.log("SSSSSSSSSSSSSSSSSS",chart.labels.length);

    
      for (var i = 0; i < chart.labels.length; i++) {
      indents.push(<Grid item lg={6} md={4} sm={6} xs={12}>
        <Accordion allowMultipleExpanded="true" allowZeroExpanded="true">
         <AccordionItem>
          <AccordionItemHeading>
              <AccordionItemButton><strong>{this.state.top10.labels[i]}</strong></AccordionItemButton>
          </AccordionItemHeading>
          <AccordionItemPanel>
            <Widget>
            
                <Typography variant="h6"><i>No of Hits: {this.state.cardData1.noOfHits[i]}</i></Typography>
                <Typography variant="h6"><i>Send: {this.state.cardData1.noOfSendTxnHits[i]}</i></Typography>
                <Typography variant="h6"><i>Cancel: {this.state.cardData1.noOfCancelTxnHits[i]}</i></Typography>
                <Typography variant="h6"><i>Enquiry: {this.state.cardData1.noOfEnquiryTxnHits[i]}</i></Typography>
                <Typography variant="h6"><i>Solr: {this.state.cardData1.noOfSolrSearchHits[i]}</i></Typography>
                <Typography variant="h6"><i>Rates: {this.state.cardData1.noOfRatesHits[i]}</i></Typography>
          
            </Widget>
            </AccordionItemPanel>
            </AccordionItem>
          </Accordion>
        </Grid>);
      }
    return(
      <React.Fragment>
        <div>
        <Grid container spacing={32}   justify="center" alignItems="flex-start">
        <Grid item md={10}>
        <Widget title={<Typography variant="h3" component="h2" gutterBottom='true'>TOP 10 Hits</Typography>} >
        <HorizontalBar
            data={this.state.top10}
            options={{
              scales:{
                yAxes: [{
                  stacked: true,
              }]
              },
                legend:{
                display: false,
                position: 'top'
                },
                onClick: function (evt,array){
                  if(array[0]){
                  console.log(array[0]._index)
                  alert("You can find full details for this agent code "+chart.labels[array[0]._index]+" from the below card deck")
                  }
                }
            }}/>
            </Widget>
            </Grid>
            </Grid>
            <Grid container spacing={32}>
              {indents}
            </Grid>
        </div>
    </React.Fragment>
    )
  }
}
