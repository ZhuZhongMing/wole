<template>
  <div class="top-right-cmp">
    <div class="chart-name">
      采集频率
      <dv-decoration-3 style="width:200px;height:20px;" />
    </div>
    <dv-charts :option="option" />
  </div>
</template>

<script>
import { getRequest } from '../../util/request'
export default {
  name: 'TopRightCmp',
  data () {
    return {
      cncmodel: {},
      option: {
        /* legend: {
          data: [
            {
              name: '供配电系统',
              color: '#f5d94e'
            }
          ],
          textStyle: {
            fill: '#fff'
          }
        }, */
        xAxis: {
          data: ['10/3', '10/4', '10/5', '10/6', '10/7'],
          axisLine: {
            style: {
              stroke: '#999'
            }
          },
          axisLabel: {
            style: {
              fill: '#999'
            }
          },
          axisTick: {
            show: false
          }
        },
        yAxis: {
          data: 'value',
          splitLine: {
            show: false
          },
          axisLine: {
            style: {
              stroke: '#999'
            }
          },
          axisLabel: {
            style: {
              fill: '#999'
            }
          },
          axisTick: {
            show: false
          },
          min: 0
        },
        series: [
          {
            data: [0, 0, 0, 0, 0],
            type: 'line',
            /* name: '供配电系统', */
            lineArea: {
              show: true,
              gradient: ['rgba(245, 217, 79, 0.8)', 'rgba(245, 217, 79, 0.2)']
            },
            lineStyle: {
              stroke: '#f5d94e'
            },
            linePoint: {
              radius: 4,
              style: {
                fill: '#f5d94e',
                stroke: 'transparent'
              }
            }
          }
        ]
      },
      url: {
        countCncModel: '/mqtt/cncModel/countCncModel'
      }
      /* option: {
        legend: {
          data: [
            {
              name: '收费系统',
              color: '#00baff'
            },
            {
              name: '监控系统',
              color: '#ff5ca9'
            },
            {
              name: '通信系统',
              color: '#3de7c9'
            },
            {
              name: '供配电系统',
              color: '#f5d94e'
            }
          ],
          textStyle: {
            fill: '#fff'
          }
        },
        xAxis: {
          data: [
            '10/01', '10/02', '10/03', '10/04', '10/05', '10/06', '10/07'
          ],
          axisLine: {
            style: {
              stroke: '#999'
            }
          },
          axisLabel: {
            style: {
              fill: '#999'
            }
          },
          axisTick: {
            show: false
          }
        },
        yAxis: {
          data: 'value',
          splitLine: {
            show: false
          },
          axisLine: {
            style: {
              stroke: '#999'
            }
          },
          axisLabel: {
            style: {
              fill: '#999'
            }
          },
          axisTick: {
            show: false
          },
          min: 0,
          max: 8
        },
        series: [
          {
            name: '收费系统',
            data: [
              2.5, 3.5, 6.5, 6.5, 7.5, 6.5, 2.5
            ],
            type: 'bar',
            barStyle: {
              fill: 'rgba(0, 186, 255, 0.4)'
            }
          },
          {
            name: '监控系统',
            data: [
              2.5, 3.5, 6.5, 6.5, 7.5, 6.5, 2.5
            ],
            type: 'line',
            lineStyle: {
              stroke: '#ff5ca9'
            },
            linePoint: {
              radius: 4,
              style: {
                fill: '#ff5ca9',
                stroke: 'transparent'
              }
            }
          },
          {
            name: '通信系统',
            data: [
              1.3, 2.3, 5.3, 5.3, 6.3, 5.3, 1.3
            ],
            type: 'line',
            smooth: true,
            lineArea: {
              show: true,
              gradient: ['rgba(55, 162, 218, 0.6)', 'rgba(55, 162, 218, 0)']
            },
            lineStyle: {
              lineDash: [5, 5]
            },
            linePoint: {
              radius: 4,
              style: {
                fill: '#00db95'
              }
            }
          },
          {
            data: [
              0.2, 1.2, 4.2, 4.2, 5.2, 4.2, 0.2
            ],
            type: 'line',
            name: '供配电系统',
            lineArea: {
              show: true,
              gradient: ['rgba(245, 217, 79, 0.8)', 'rgba(245, 217, 79, 0.2)']
            },
            lineStyle: {
              stroke: '#f5d94e'
            },
            linePoint: {
              radius: 4,
              style: {
                fill: '#f5d94e',
                stroke: 'transparent'
              }
            }
          }
        ]
      } */
    }
  },
  methods: {
    getCNCModel6 (cncmodel) {
      this.cncmodel = cncmodel
      this.countCncModel()
    },
    countCncModel () {
      if (this.cncmodel.cncsn) {
        getRequest(this.url.countCncModel, this.cncmodel).then(res => {
          console.log(res.data.result)
          if (res.data.result) {
            var result = res.data.result
            var x = []
            var y = []
            result.forEach((item, index) => {
              x.push(item.alarminfo)
              y.push(item.count)
            })
            console.log('x : ' + x)
            console.log('y : ' + y)
            this.option = {
              xAxis: {
                data: x,
                axisLine: {
                  style: {
                    stroke: '#999'
                  }
                },
                axisLabel: {
                  style: {
                    fill: '#999'
                  }
                },
                axisTick: {
                  show: false
                }
              },
              yAxis: {
                data: 'value',
                splitLine: {
                  show: false
                },
                axisLine: {
                  style: {
                    stroke: '#999'
                  }
                },
                axisLabel: {
                  style: {
                    fill: '#999'
                  }
                },
                axisTick: {
                  show: false
                },
                min: 0,
              },
              series: [
                {
                  data: y,
                  type: 'line',
                  lineArea: {
                    show: true,
                    gradient: ['rgba(245, 217, 79, 0.8)', 'rgba(245, 217, 79, 0.2)']
                  },
                  lineStyle: {
                    stroke: '#f5d94e'
                  },
                  linePoint: {
                    radius: 4,
                    style: {
                      fill: '#f5d94e',
                      stroke: 'transparent'
                    }
                  }
                }
              ]
            }
          }
        }).catch(exc => {
          console.log('查询设备采集频率！异常信息：' + exc)
        })
      }
    }
  }
}
</script>

<style lang="less">
.top-right-cmp {
  position: relative;
  padding: 0 50px;
  box-sizing: border-box;

  .chart-name {
    position: absolute;
    right: 70px;
    text-align: right;
    font-size: 20px;
    top: 10px;
  }
}
</style>
