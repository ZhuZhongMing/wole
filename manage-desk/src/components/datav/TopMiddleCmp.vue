<template>
  <div class="top-middle-cmp">
    <div class="chart-name">
      设备生产趋势
      <dv-decoration-3 style="width:200px;height:20px;" />
    </div>
    <dv-charts :option="option" />
  </div>
</template>

<script>
import { getRequest } from '../../util/request'
export default {
  name: 'TopMiddleCmp',
  data () {
    return {
      option: {
        /* legend: {
          data: ['设备产量'],
          textStyle: {
            fill: '#fff'
          }
        }, */
        xAxis: {
          data: [
            '10/01', '10/02', '10/03', '10/04', '10/05', '10/06'
          ],
          boundaryGap: false,
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
            },
            formatter ({ value }) {
              return value.toFixed(2)
            }
          },
          axisTick: {
            show: false
          },
          min: 0
        },
        series: [
          {
            data: [
              0, 0, 0, 0, 0, 0
            ],
            type: 'line',
            name: '设备产量',
            smooth: true,
            lineArea: {
              show: true,
              gradient: ['rgba(55, 162, 218, 0.6)', 'rgba(55, 162, 218, 0)']
            },
            linePoint: {
              radius: 4,
              style: {
                fill: '#00db95'
              }
            }
          }
        ]
      },
      cncmodel: {},
      url: {
        sumDaily: '/system/dailyCapacity/sumDaily'
      }
    }
  },
  methods: {
    getCNCModel7 (cncmodel) {
      this.cncmodel = cncmodel
      this.sumDaily()
    },
    sumDaily () {
      if (this.cncmodel.cncsn) {
        var parm = {
          equipmentsn: this.cncmodel.cncsn
        }
        getRequest(this.url.sumDaily, parm).then(res => {
          if (res.data.result) {
            var result = res.data.result
            var x = []
            var y = []
            result.forEach((item, index) => {
              x.push(item.equipmentsn)
              y.push(item.count)
            })
            console.log('x : ' + x)
            console.log('y : ' + y)
            /* this.option = {
              xAxis: {
                data: x,
                boundaryGap: false,
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
                  },
                  formatter ({ value }) {
                    return value.toFixed(2)
                  }
                },
                axisTick: {
                  show: false
                },
                min: 0
              },
              series: [
                {
                  data: y,
                  type: 'line',
                  name: '设备产量',
                  smooth: true,
                  lineArea: {
                    show: true,
                    gradient: ['rgba(55, 162, 218, 0.6)', 'rgba(55, 162, 218, 0)']
                  },
                  linePoint: {
                    radius: 4,
                    style: {
                      fill: '#00db95'
                    }
                  }
                }
              ]
            } */
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
                min: 0
              },
              series: [
                {
                  data: y,
                  type: 'line',
                  lineArea: {
                    show: true,
                    gradient: ['rgba(55, 162, 218, 0.6)', 'rgba(55, 162, 218, 0)']
                  },
                  lineStyle: {
                    stroke: '#00db95'
                  },
                  linePoint: {
                    radius: 4,
                    style: {
                      fill: '#00db95',
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
  },
  mounted () {
  }
}
</script>

<style lang="less">
  .top-middle-cmp {
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
