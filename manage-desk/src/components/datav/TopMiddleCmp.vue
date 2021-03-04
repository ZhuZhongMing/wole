<template>
  <div class="top-middle-cmp">
    <div class="chart-name">
      设备生产月趋势1
      <dv-decoration-3 style="width:200px;height:20px;" />
    </div>
    <dv-charts :option="option" />
    <!--<div id="container" style="width: 100%; height: 100%; background-color: rgba(0, 0, 0, 0)"></div>-->
  </div>
</template>

<script>
import * as Three from 'three'
export default {
  name: 'TopMiddleCmp',
  data () {
    return {
      option: {
        legend: {
          data: ['设备产量'],
          textStyle: {
            fill: '#fff'
          }
        },
        xAxis: {
          data: [
            '10/01', '10/02', '10/03', '10/04', '10/05', '10/06',
            '10/07', '10/07', '10/08', '10/09', '10/10', '10/11',
            '10/12', '10/13', '10/14', '10/15'
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
          min: 95,
          max: 100,
          interval: 0.5
        },
        series: [
          {
            data: [
              99.56, 99.66, 99.84, 99.22, 99.11, 99.45,
              99.44, 99.81, 99.84, 99.32, 99.14, 99.45,
              99.15, 99.45, 99.64, 99.89
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
      camera: null,
      scene: null,
      renderer: null,
      mesh: null
    }
  },
  methods: {
    init: function () {
      let container = document.getElementById('container')
      this.camera = new Three.PerspectiveCamera(70, container.clientWidth / container.clientHeight, 0.01, 10)
      this.camera.position.z = 0.6
      /**
         * 创建场景对象Scene
         */
      this.scene = new Three.Scene()
      // this.scene.background = new Three.Color(0xa0a0a0) // 设置透明背景
      /**
         * 创建网格模型
         */
      // var geometry = new THREE.SphereGeometry(60, 40, 40); //创建一个球体几何对象
      let geometry = new Three.BoxGeometry(0.2, 0.2, 0.2) // 创建一个立方体几何对象Geometry
      let material = new Three.MeshNormalMaterial() // 材质对象Material
      this.mesh = new Three.Mesh(geometry, material) // 网格模型对象Mesh
      this.scene.add(this.mesh) // 网格模型添加到场景中
      /**
         * 创建渲染器对象
         */
      this.renderer = new Three.WebGLRenderer({ antialias: true, alpha: true })
      // this.renderer.setClearColor(0xb9d3ff, 1)
      this.renderer.setClearAlpha(0) // #002153
      // this.renderer.setClearColor(0xffffff, 0)
      // this.renderer.setClearColor(0xEEEEEE, 0.0) // 设置背景颜色
      this.renderer.setSize(container.clientWidth, container.clientHeight) // 设置渲染区域尺寸
      container.appendChild(this.renderer.domElement) // 元素中插入canvas对象
    },
    animate: function () {
      requestAnimationFrame(this.animate)
      this.mesh.rotation.x += 0.01
      this.mesh.rotation.y += 0.02
      this.renderer.render(this.scene, this.camera)
    }
  },
  mounted () {
    /* this.init()
    this.animate() */
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
