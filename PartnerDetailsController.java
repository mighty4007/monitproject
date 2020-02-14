import { Typography } from "@material-ui/core";
import Grid from '@material-ui/core/Grid';
import Axios from 'axios';
import React, { Component } from "react";
import { Accordion, AccordionItem, AccordionItemButton, AccordionItemHeading, AccordionItemPanel } from 'react-accessible-accordion';
import 'react-accessible-accordion/dist/fancy-example.css';
import { HorizontalBar } from 'react-chartjs-2';
import Widget from "../../components/Widget";

import ReactApexChart from 'react-apexcharts'


export default class Dashboard extends Component {


  state = {

    series: [],
    options: {},
    top10: { labels: [] },
    methodHits: { entityCode: [], txnHits: [], enquiryHits: [], totalHits: [] },
    testResults: null

  }

  componentWillMount() {
    document.title = 'Load Monitoring'
    this.fetchData();
    this.fetchMethodHits();
  }

  fetchMethodHits() {
    Axios.get('http://localhost:8010/getMethodHits/').then(res => {

      const entityCode = [];
      const txnHits = [];
      const enquiryHits = [];
      const totalHits = [];

      for (const key in res.data) {
        entityCode[key] = res.data[key].entityCode
        txnHits[key] = res.data[key].txnHits
        enquiryHits[key] = res.data[key].enquiryHits
        totalHits[key] = res.data[key].totalHits
      }

      this.setState({
        methodHits: {
          entityCode: entityCode,
          txnHits: txnHits,
          enquiryHits: enquiryHits,
          totalHits: totalHits
        }
      })

    })
  }

  fetchData() {
    console.log("in did getAllEntityData STAdsadsdsdasds")
    Axios.defaults.baseURL = 'http://myurl';
    Axios.defaults.headers.post['Content-Type'] = 'application/json;charset=utf-8';
    Axios.defaults.headers.post['Access-Control-Allow-Origin'] = '*';

    Axios.get('http://localhost:8010/getAllEntityData/').then(entity_res => {

      const entity_labels = [];
      const entity_methods = [];

      console.log("in did getAllEntityData STATE", entity_res)

      const response = entity_res.data;
      const hitDataGroupBy_Entity = response.hitDataGroupBy_Entity;
      const hitDataGroupBy_Entity_Method = response.hitDataGroupBy_Entity_Method;
      const chartData = response.chartData;


      console.log("hitDataGroupBy_Entity:", hitDataGroupBy_Entity)
      console.log("hitDataGroupBy_Entity_Method:", hitDataGroupBy_Entity_Method)
      console.log("chartData:", chartData)

      for (const key in hitDataGroupBy_Entity) {
        entity_labels[key] = hitDataGroupBy_Entity[key].entityCode
      }


      console.log("entity_labels:", entity_labels)

      this.setState({
        top10: {
          labels: entity_labels
        },
        testResults: response
      })

      this.setState({


        options: {
          chart: {
            type: 'bar',
            height: 350,
            stacked: true,
          },
          plotOptions: {
            bar: {
              horizontal: true,
            },
          },
          stroke: {
            width: 1,
            colors: ['#fff']
          },
          title: {
            text: 'Top 10 Hitters Method Wise'
          },
          xaxis: {
            categories: entity_labels,
            labels: {
              formatter: function (val) {
                return val
              }
            }
          },
          yaxis: {
            title: {
              text: undefined
            },
          },
          tooltip: {
            y: {
              formatter: function (val) {
                return val
              }
            }
          },
          fill: {
            opacity: 1
          },
          legend: {
            position: 'top',
            horizontalAlign: 'left',
            offsetX: 40
          }
        },

        series: chartData,

      })



    }
    )

  }



  render() {
    const { testResults } = this.state;
    console.log(testResults);

    // const filteredData = testResults.hitDataGroupBy_Entity.map(et => {
    //   let match = testResults.hitDataGroupBy_EntityMethod.filter(em => {
    //     if (em.entityCode === et.entityCode) return em
    //   })
    //   return match
    // })

    var indents = [];
    {
      testResults &&
        // for (var i = 0; i < hitDataGroupBy_Entity.length; i++) {
        testResults.hitDataGroupBy_Entity.map(entity => (
          indents.push(
            <Grid item lg={6} md={4} sm={6} xs={12}>
              <Accordion allowMultipleExpanded="true" allowZeroExpanded="true">
                <AccordionItem>
                  <AccordionItemHeading>
                    <AccordionItemButton><strong>{entity.entityCode}</strong></AccordionItemButton>
                  </AccordionItemHeading>
                  <AccordionItemPanel>
                    <Widget>
                      {testResults.hitDataGroupBy_Entity_Method.map(method => (
                        entity.entityCode === method.entityCode &&
                        <Typography variant="h6"><i>{method.methodName.toUpperCase()}: {method.hitCount}</i></Typography>
                      ))}
                      <Typography variant="h6"><i>Total Hits: {entity.hitCount}</i></Typography>
                      {/* <Typography variant="h6"><i>Enquiry Hits: {this.state.methodHits.enquiryHits[i]}</i></Typography>
                      <Typography variant="h6"><i>Total Hits: {this.state.methodHits.totalHits[i]}</i></Typography> */}
                    </Widget>
                  </AccordionItemPanel>
                </AccordionItem>
              </Accordion>
            </Grid>)
        ))
      // }
    }
    return (
      <div>
        <div id="chart">
          <ReactApexChart options={this.state.options} series={this.state.series} type="bar" height={350} />
        </div>
        {/* <div id="html-dist"></div> */}
        <Grid container spacing={32}>
          {indents}
        </Grid>
      </div >
    );
  }


}
