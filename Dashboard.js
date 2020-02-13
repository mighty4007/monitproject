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
    options: {}
      
  }

  componentWillMount() {
    document.title = 'Load Monitoring'
    this.fetchData();
  }

  fetchData() {
    console.log("in did getAllEntityData STAdsadsdsdasds")
    Axios.defaults.baseURL = 'http://myurl';
    Axios.defaults.headers.post['Content-Type'] = 'application/json;charset=utf-8';
    Axios.defaults.headers.post['Access-Control-Allow-Origin'] = '*';

    Axios.get('http://localhost:8888/getAllEntityData/').then(entity_res => {

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
                return val + "K"
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
    return (
      <div>
        <div id="chart">
          <ReactApexChart options={this.state.options} series={this.state.series} type="bar" height={350} />
        </div>
        {/* <div id="html-dist"></div> */}
      </div>
    );
  }

 
}
