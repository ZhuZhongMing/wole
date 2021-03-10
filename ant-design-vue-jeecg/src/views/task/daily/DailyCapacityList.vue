<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="设备名称">
              <j-dict-select-tag type="list" v-model="queryParam.equipmentsn" dictCode="mbp_dev_equipment,equipment_name,equipment_id" placeholder="请选择设备编号"/>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="开始日期">
              <!--<a-input v-model="queryParam.orderId" placeholder="请输入订单编号"/>-->
              <!--<a-date-picker placeholder="请选择创建时间" v-model="queryParam.createTime" style="width: 100%"/>-->
              <j-date placeholder="请选择创建时间" v-model="queryParam.createTime_begin" :trigger-change="true" :show-time="true" date-format="YYYY-MM-DD" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="结束日期">
              <!--<a-input v-model="queryParam.orderId" placeholder="请输入订单编号"/>-->
              <!--<a-date-picker placeholder="请选择创建时间" v-model="queryParam.createTime" style="width: 100%"/>-->
              <j-date placeholder="请选择创建时间" v-model="queryParam.createTime_end" :trigger-change="true" :show-time="true" date-format="YYYY-MM-DD" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span class="table-page-search-submitButtons table-operator">
              <a-button type="primary" icon="search" @click="searchQuery">查询</a-button>
              <a-button type="primary" icon="reload" @click="searchReset">重置</a-button>
              <a @click="handleToggleSearch" style="margin-left: 8px">
                <span>{{ toggleSearchStatus ? '收起' : '展开' }}</span>
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
              </a>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->



    <a-tabs default-active-key="1">
      <a-tab-pane key="1" tab="数据列表">
        <!-- 操作按钮区域 -->
        <div class="table-operator">
          <!--<a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>-->
          <a-button type="primary" icon="download" @click="handleExportXls('设备每日参能')">导出</a-button>
          <!--<a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
            <a-button type="primary" icon="import">导入</a-button>
          </a-upload>-->
          <a-dropdown v-if="selectedRowKeys.length > 0">
            <a-menu slot="overlay">
              <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
            </a-menu>
            <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
          </a-dropdown>
        </div>

        <!-- table区域-begin -->
        <div>
          <!--<div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
            <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
            <a style="margin-left: 24px" @click="onClearSelected">清空</a>
          </div>-->

          <a-table
            ref="table"
            size="middle"
            :scroll="{x:true}"
            bordered
            rowKey="id"
            :columns="columns"
            :dataSource="dataSource"
            :pagination="ipagination"
            :loading="loading"
            class="j-table-force-nowrap"
            @change="handleTableChange">
            <!--:rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"-->
            <template slot="htmlSlot" slot-scope="text">
              <div v-html="text"></div>
            </template>
            <template slot="imgSlot" slot-scope="text">
              <span v-if="!text" style="font-size: 12px;font-style: italic;">无图片</span>
              <img v-else :src="getImgView(text)" height="25px" alt="" style="max-width:80px;font-size: 12px;font-style: italic;"/>
            </template>
            <template slot="fileSlot" slot-scope="text">
              <span v-if="!text" style="font-size: 12px;font-style: italic;">无文件</span>
              <a-button
                v-else
                :ghost="true"
                type="primary"
                icon="download"
                size="small"
                @click="downloadFile(text)">
                下载
              </a-button>
            </template>

            <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a @click="handleDetail(record)">详情</a>
              </a-menu-item>
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

          </a-table>
        </div>
      </a-tab-pane>
      <a-tab-pane key="2" tab="产量对比" force-render>
        <a-skeleton v-if="chartFlag == false" />
        <bar v-else :dataSource="barData" :height="500" style="width: 95%;" :yaxisText="y"/>
      </a-tab-pane>
    </a-tabs>

    <daily-capacity-modal ref="modalForm" @ok="modalFormOk"></daily-capacity-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import DailyCapacityModal from './modules/DailyCapacityModal'
  import {filterMultiDictText} from '@/components/dict/JDictSelectUtil'
  import ACol from 'ant-design-vue/es/grid/Col'
  import JDate from '@/components/jeecg/JDate'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  import { getAction } from '@/api/manage'
  import Bar from '@/components/chart/Bar'

  export default {
    name: 'DailyCapacityList',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      ACol,
      DailyCapacityModal,
      JDate,
      JDictSelectTag,
      Bar
    },
    data () {
      return {
        y: '产量',
        chartFlag: false,
        barData: [],
        description: '设备每日参能管理页面',
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
          },
          {
            title:'设备编号',
            align:"center",
            dataIndex: 'equipmentsn_dictText'
          },
          {
            title:'数量',
            align:"center",
            dataIndex: 'count'
          },
          {
            title:'创建时间',
            align:"center",
            dataIndex: 'createTime'
          },
          {
            title:'修改时间',
            align:"center",
            dataIndex: 'updateTime'
          },
          /*{
            title:'设备上传的实际数量',
            align:"center",
            dataIndex: 'equipmentcount'
          },
          {
            title:'生产计划记录过的数量',
            align:"center",
            dataIndex: 'taskcount'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            fixed:"right",
            width:147,
            scopedSlots: { customRender: 'action' }
          }*/
        ],
        url: {
          list: "/system/dailyCapacity/list",
          delete: "/system/dailyCapacity/delete",
          deleteBatch: "/system/dailyCapacity/deleteBatch",
          exportXlsUrl: "/system/dailyCapacity/exportXls",
          importExcelUrl: "system/dailyCapacity/importExcel",
          groupByEquipment: 'system/dailyCapacity/groupByEquipment'
        },
        dictOptions:{},
      }
    },
    created() {
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      },
    },
    methods: {
      initDictConfig(){
      },
      /*点击查询*/
      searchQuery() {
        this.loadData(1);
      },
      /*加载列表数据*/
      loadData(arg) {
        if(!this.url.list){
          this.$message.error("请设置url.list属性!")
          return
        }
        //加载数据 若传入参数1则加载第一页的内容
        if (arg === 1) {
          this.ipagination.current = 1;
        }
        var params = this.getQueryParams();//查询条件
        this.loading = true;
        getAction(this.url.list, params).then((res) => {
          if (res.success) {
            this.dataSource = res.result.records;
            this.ipagination.total = res.result.total;
            this.loadChartData(params)
          }
          if(res.code===510){
            this.$message.warning(res.message)
          }
          this.loading = false;
        })
      },
      /*加载图表数据*/
      loadChartData(params) {
        this.chartFlag = false
        if (params.createTime_begin) {
          params.createTime_begin = params.createTime_begin + " 00:00:00:001"
        }
        if (params.createTime_end) {
          params.createTime_end = params.createTime_end + " 23:59:59:999"
        }
        getAction(this.url.groupByEquipment, params).then((res) => {
          if (res.success) {
            //console.log("图表数据 : " + JSON.stringify(res.result))
            this.barData.splice(0)
            if (res.result.length > 0) {
              for (let i = 0; i < res.result.length; i += 1) {
                if (null == res.result[i].count || 'null' == res.result[i].count) {
                  this.barData.push({
                    x: res.result[i].equipmentName,
                    y: 0
                  })
                } else {
                  this.barData.push({
                    x: res.result[i].equipmentName,
                    y: res.result[i].count
                  })
                }
              }
              this.chartFlag = true
            } else {
              this.$message.warning("未找到数据")
              this.chartFlag = false
            }

          }
          if(res.code===510){
            this.$message.warning(res.message)
          }
        })
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>