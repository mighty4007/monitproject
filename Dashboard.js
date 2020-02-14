import { Typography } from "@material-ui/core";
import Grid from '@material-ui/core/Grid';
import Axios from 'axios';
import React, { Component } from "react";
import { Accordion, AccordionItem, AccordionItemButton, AccordionItemHeading, AccordionItemPanel } from 'react-accessible-accordion';
import 'react-accessible-accordion/dist/fancy-example.css';
import ReactApexChart from 'react-apexcharts';
import Widget from "../../components/Widget";
import Chart from "react-apexcharts";


export default class Dashboard extends Component {


  state = {
    totalCount: 0,
    series: [],
    options: {},
    optionsRadial: {
      plotOptions: {
        radialBar: {
          startAngle: -135,
          endAngle: 225,
          hollow: {
            margin: 0,
            size: "70%",
            background: "#fff",
            image: undefined,
            imageOffsetX: 0,
            imageOffsetY: 0,
            position: "front",
            dropShadow: {
              enabled: true,
              top: 3,
              left: 0,
              blur: 4,
              opacity: 0.24
            }
          },
          track: {
            background: "#fff",
            strokeWidth: "67%",
            margin: 0, // margin is in pixels
            dropShadow: {
              enabled: true,
              top: -3,
              left: 0,
              blur: 4,
              opacity: 0.35
            }
          },

          dataLabels: {
            showOn: "always",
            name: {
              offsetY: -20,
              show: true,
              color: "#888",
              fontSize: "13px"
            },
            value: {
              formatter: function (val) {
                return val;
              },
              color: "#111",
              fontSize: "30px",
              show: true
            }
          }
        }
      },
      fill: {
        type: "gradient",
        gradient: {
          shade: "dark",
          type: "horizontal",
          shadeIntensity: 0.5,
          gradientToColors: ["#ABE5A1"],
          inverseColors: true,
          opacityFrom: 1,
          opacityTo: 1,
          stops: [0, 100]
        }
      },
      stroke: {
        lineCap: "round"
      },
      labels: ["Percent"]
    },
    seriesRadial: [10.58],

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
      // let totalcount = 0;

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


      // totalcount = 

      // hitDataGroupBy_Entity.map(ent => (
      //   tot
      // ))

      // for (var i = 0; i < hitDataGroupBy_Entity.length; i++) {
      //   console.log(hitDataGroupBy_Entity[i]);

      //   totalcount = totalcount + hitDataGroupBy_Entity[i].hitCount
      // }


      console.log("entity_labels:", entity_labels)
      console.log("hit count", hitDataGroupBy_Entity.reduce((sum, entity) => sum + entity.hitCount, 0))

      this.setState({

        totalCount: hitDataGroupBy_Entity.reduce((sum, entity) => sum + entity.hitCount, 0),
        apiDataSet: entity_res.data,
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

        optionsRadial: {
          plotOptions: {
            radialBar: {
              startAngle: -135,
              endAngle: 225,
              hollow: {
                margin: 0,
                size: "70%",
                background: "#fff",
                image: undefined,
                imageOffsetX: 0,
                imageOffsetY: 0,
                position: "front",
                dropShadow: {
                  enabled: true,
                  top: 3,
                  left: 0,
                  blur: 4,
                  opacity: 0.24
                }
              },
              track: {
                background: "#fff",
                strokeWidth: "67%",
                margin: 0, // margin is in pixels
                dropShadow: {
                  enabled: true,
                  top: -3,
                  left: 0,
                  blur: 4,
                  opacity: 0.35
                }
              },

              dataLabels: {
                showOn: "always",
                name: {
                  offsetY: -20,
                  show: true,
                  color: "#888",
                  fontSize: "13px"
                },
                value: {
                  formatter: function (val) {
                    return val;
                  },
                  color: "#111",
                  fontSize: "30px",
                  show: true
                }
              }
            }
          },
          fill: {
            type: "gradient",
            gradient: {
              shade: "dark",
              type: "horizontal",
              shadeIntensity: 0.5,
              gradientToColors: ["#ABE5A1"],
              inverseColors: true,
              opacityFrom: 1,
              opacityTo: 1,
              stops: [0, 100]
            }
          },
          stroke: {
            lineCap: "round"
          },
          labels: ["Percent"]
        }
      })

    }
    )

  }



  render() {
    const { apiDataSet, totalCount } = this.state;
    console.log(totalCount);

    var indents = [];
    {

      apiDataSet &&
        apiDataSet.hitDataGroupBy_Entity.map(entity => (
          indents.push(
            <Grid item lg={6} md={4} sm={6} xs={12}>

              <Accordion allowMultipleExpanded="true" allowZeroExpanded="true">
                <AccordionItem>
                  <AccordionItemHeading>
                    <AccordionItemButton><strong>{entity.entityCode}</strong></AccordionItemButton>
                  </AccordionItemHeading>
                  <AccordionItemPanel>
                    <Widget>
                      {apiDataSet.hitDataGroupBy_Entity_Method.map(method => (

                        entity.entityCode === method.entityCode &&
                        <Typography variant="h6"><i>{method.methodName.toUpperCase()}: {method.hitCount}</i></Typography>

                      ))}
                      <Typography variant="h6"><i>Total Hits: {entity.hitCount}</i></Typography>

                      <Chart
                        options={this.state.optionsRadial}
                        series={[((entity.hitCount / this.state.totalCount) * 100).toFixed(1)]}
                        type="radialBar"
                        width="280"
                      />
                    </Widget>
                  </AccordionItemPanel>
                </AccordionItem>
              </Accordion>
            </Grid>)
        ))

    }


    return (
      <div>
        <div id="chart">
          <ReactApexChart options={this.state.options} series={this.state.series} type="bar" height={350} />
        </div>
        <div>
          <Grid container spacing={32}>
            {indents}
          </Grid>
        </div>


      </div>



    );
  }


}
