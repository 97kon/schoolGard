<template>
    <!--    当前页面为商品管理——商品列表的vue组件-->
    <div class="box">
        <!--        下面的p是商品列表的标题-->
        <div class="text">
            <p style="font-size: 17px;font-weight: bold;margin-left: 15px;padding-top: 10px">查询入校记录</p>
        </div>


        <div class="head_one">
            <!--            下面的每一个head_box的块级元素都用的flex弹性布局让其里面水平-->
            <div class="head_box">
                <p>姓名</p>
                <el-input v-model="filterForm.name" type="text" placeholder="姓名"></el-input>
            </div>
            <div class="head_box">
                <p>手机号</p>
                <el-input v-model="filterForm.phone" type="text" placeholder="手机号"></el-input>
            </div>
            <div class="head_box">
                <p>身份证号</p>
                <el-input v-model="filterForm.idNumber" type="text" placeholder="身份证号"></el-input>
            </div>
            <div class="head_box">
                <p>审批状态</p>
                <!--                <el-input ></el-input>-->
                <el-select v-model="filterForm.status" placeholder="审批状态" style="height: 35px" clearable>
                    <el-option value="">全部</el-option>
                    <el-option value="0">待审核</el-option>
                    <el-option value="1">已通过</el-option>
                    <el-option value="2">未通过</el-option>
                    <el-option value="3">已取消</el-option>
                    <el-option value="4">已爽约</el-option>


                </el-select>
            </div>
        </div>
        <!--        下面的head_two是筛选模块的第二行的内容（选择时间）-->
        <div class="head_two">
            <p>选择时间</p>
            <div class="demo_range">
                <!--                这里用的elementplus的组件——时间选择器-->
            <el-date-picker
                    v-model="dateRange"
                    type="datetimerange"
                    range-separator="至"
                    start-placeholder="开始日期时间"
                    end-placeholder="结束日期时间"
                    format="YYYY-MM-DD HH:mm"
                    value-format="YYYY-MM-DD HH:mm"
                />
            </div>
            <el-button type="primary" @click="handleSearch" style="margin-top: 8px;">查询</el-button>
            <el-button type="primary" @click="filterToday" style="margin-top: 8px;margin-left: 10px;">一键筛选当天入校</el-button>
            <el-button type="primary" @click="generateReport" style="margin-top: 8px;margin-left: 10px;">生成数据报表</el-button>
        </div>
        <!--        下面的table类是数据展示部分-->
        <div class="table">
            <!--            依旧是通过:data跟js里面的数据进行双向数据绑定-->
            <el-table :data="paginatedData" :border="parentBorder" style="width: 100%;font-size: 13px">
                <!--                下面的这个column通过type属性设置了一个expand表示这个数据表是列可折叠的样式-->
                <el-table-column type="expand">
                    <!--                    下面就是每一行数据被折叠的部分里面显示的数据-->
                    <template #default="props">
                        <div style="display: flex">
                            <!--                            左右两部分展示，设置了一个弹性布局-->
                            <div m="5" style="margin-left: 50px;margin-right: 300px">
                                <!--                                下面的每一个p标签里面都通过vue特有的声明式渲染跟table里面的prop进行绑定-->
                                <p m="t-0 b-2">姓名： {{ props.row.aname }}</p>
                                <p m="t-0 b-2">性别： {{ props.row.asex }}</p>
                                <p m="t-0 b-2">身份证号： {{ props.row.aidnumber }}</p>
                                <p m="t-0 b-2">手机号： {{ props.row.aphone }}</p>
                                <p m="t-0 b-2">入校事由: {{ props.row.adesc }}</p>
                            </div>
                            <div m="4">
                                <p m="t-0 b-2">进校时间： {{ props.row.aentertime }}</p>
                                <p m="t-0 b-2">离校时间： {{ props.row.aouttime }}</p>
                                <p m="t-0 b-2">申请提交时间： {{ props.row.applisubtime }}</p>
                                <p m="t-0 b-2">审批状态：
                                  <el-tag :type="getStatusTagType(props.row.astatus)" effect="dark">
                                    {{ getStatusText(props.row.astatus) }}
                                  </el-tag>
                                </p>
<!--                                <p m="t-0 b-2">商品销量： {{ props.row.goodsSell }}</p>-->
                            </div>
    </div>

    <!-- 编辑对话框 -->
    <el-dialog v-model="editDialogVisible" title="编辑入校记录" width="600px">
      <el-form :model="editForm" label-width="80px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="姓名">
              <el-input v-model="editForm.aname"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="手机号">
              <el-input v-model="editForm.aphone"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="身份证号">
              <el-input v-model="editForm.aidnumber"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="审批状态">
              <el-select v-model="editForm.astatus" style="width:100%">
                <el-option value="0" label="待审核"></el-option>
                <el-option value="1" label="已通过"></el-option>
                <el-option value="2" label="未通过"></el-option>
                <el-option value="3" label="已取消"></el-option>
                <el-option value="4" label="已爽约"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="入校事由">
          <el-input type="textarea" v-model="editForm.adesc" :rows="3"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="editDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="confirmEdit">提交</el-button>
        </span>
      </template>
    </el-dialog>
</template>
                </el-table-column>
                <!--                后面的column依旧是通过prop跟tableData[]里面的数据进行键值对的绑定-->
                <el-table-column label="姓名" width="80" prop="aname" />
                <el-table-column label="记录编号" prop="anumbering" />
                <el-table-column label="手机号" prop="aphone" />
                <el-table-column label="进校时间" prop="aentertime" />
                <el-table-column label="离校时间" prop="aouttime" />
                <el-table-column label="审批状态">
                  <template #default="scope">
                    <el-tag :type="getStatusTagType(scope.row.astatus)" effect="dark">
                      {{ getStatusText(scope.row.astatus) }}
                    </el-tag>
                  </template>
                </el-table-column>
                <el-table-column label="操作">
                    <template #default="scope">
                        <el-button link size="small" type="primary" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                        <el-button link size="small" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <!--        下面是一个分页组件-->
        <div class="pagination" style="margin-top: 30px;padding-bottom: 20px;padding-left: 15px">
            <el-pagination
                :current-page="currentPage"
                :page-size="pageSize"
                layout="total,prev, pager, next,jumper"
                :total="filteredData.length"
                @current-change="handleCurrentChange"
            />
        </div>
    </div>

    <!-- 编辑对话框 -->
    <el-dialog v-model="editDialogVisible" title="编辑入校记录" width="600px">
      <el-form :model="editForm" label-width="80px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="姓名">
              <el-input v-model="editForm.aname"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="手机号">
              <el-input v-model="editForm.aphone"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="身份证号">
              <el-input v-model="editForm.aidnumber"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="审批状态">
              <el-select v-model="editForm.astatus" style="width:100%">
                <el-option value="0" label="待审核"></el-option>
                <el-option value="1" label="已通过"></el-option>
                <el-option value="2" label="未通过"></el-option>
                <el-option value="3" label="已取消"></el-option>
                <el-option value="4" label="已爽约"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="入校事由">
          <el-input type="textarea" v-model="editForm.adesc" :rows="3"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="editDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="confirmEdit">提交</el-button>
        </span>
      </template>
    </el-dialog>
</template>

<script>
import * as XLSX from 'xlsx';
import { saveAs } from 'file-saver';

export default {
    name: "selectEnterRecord",
    data(){
        return {
          currentPage: 1,
          pageSize: 8,
          total: 15, // 根据当前tableData长度设置
          dateRange: [],
          filterForm: {
            name: '',
            phone: '',
            idNumber: '',
            status: '',
          },
          allData: [],
          statusMap: {
            '0': { type: 'warning', text: '待审核' },
            '1': { type: 'success', text: '已通过' },
            '2': { type: 'danger', text: '未通过' },
            '3': { type: 'info', text: '已取消' },
            '4': { type: 'primary', text: '已爽约' }
          },
            editDialogVisible: false,
            editForm: {
              aname: '',
              asex: '',
              aidnumber: '',
              aphone: '',
              acar: '',
              aentertime: '',
              aouttime: '',
              adesc: '',
              astatus: '',
              anumbering: '',
              applisubtime: ''
            },
            tableData:[
                // {
                //     aname: '刘某',
                //     asex: '男',
                //     aidnumber: '511724200000000000',
                //     aphone: '13900004399',
                //     acar: '川A12345',
                //     aentertime: '2025/4/20 21:37',
                //     aouttime: '2025/4/21 21:37',
                //     adesc: '回学校参加毕业答辩',
                //     astatus: '0',
                //     anumbering: 'ar328945789',
                //     applisubtime: '2025/4/16 11:28'
                // },


            ]
        }
    },
    created() {
        this.fetchApplyrecordList()
      this.allData = [...this.tableData];

    },
    computed: {
        filteredData() {
          return this.allData.filter(item => {
            const nameMatch = !this.filterForm.name || item.aname.includes(this.filterForm.name);
            const phoneMatch = !this.filterForm.phone || item.aphone.includes(this.filterForm.phone);
            const idMatch = !this.filterForm.idNumber || item.aidnumber.includes(this.filterForm.idNumber);
            const statusMatch = !this.filterForm.status || item.astatus.toString() === this.filterForm.status.toString();

            console.log("后端状态嘛："+item.astatus)
              console.log("前端筛选的："+this.filterForm.status)
              console.log(item.astatus === this.filterForm.status)
              console.log(item.astatus.type)
              console.log(this.filterForm.status.type)

            let dateMatch = true;
            if (this.dateRange && this.dateRange.length === 2) {
              const startDate = new Date(this.dateRange[0]);
              const endDate = new Date(this.dateRange[1]);
              const enterDate = new Date(item.aentertime);
              dateMatch = enterDate >= startDate && enterDate <= endDate;
            }

            return nameMatch && phoneMatch && idMatch && statusMatch && dateMatch;
          });
        },
        paginatedData() {
            const start = (this.currentPage - 1) * this.pageSize;
            const end = start + this.pageSize;
            return this.filteredData.slice(start, end);
        }
    },
    methods: {
        formatDateTime(isoString) {
            if (!isoString) return '';
            const date = new Date(isoString);
            const year = date.getFullYear();
            const month = String(date.getMonth() + 1).padStart(2, '0');
            const day = String(date.getDate()).padStart(2, '0');
            const hours = String(date.getHours()).padStart(2, '0');
            const minutes = String(date.getMinutes()).padStart(2, '0');
            const seconds = String(date.getSeconds()).padStart(2,'0');
            return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
        },
        async fetchApplyrecordList(){
            try{
                const res = await this.$axios.get('http://localhost:8090/api/manage/applyrecord/list');
                console.log('API响应:', res);
                this.tableData = res.data.map(item => ({
                    aname: item.aname,
                    asex: item.asex,
                    aidnumber: item.aidnumber,
                    aphone: item.aphone,
                    acar: item.acar,
                    aentertime: this.formatDateTime(item.aentertime),
                    aouttime: this.formatDateTime(item.aouttime),
                    adesc: item.adesc,
                    astatus: item.astatus,
                    anumbering: item.anumbering,
                    applisubtime: this.formatDateTime(item.applisubtime)
                }));
                this.allData = [...this.tableData];
                console.log('res',res.data)
                console.log('tableData:', this.tableData);
                console.log('allDasta:', this.allData);
                this.$message.success("获取成功");
            }catch (error){
                console.error('获取登记记录失败', error);
                this.$message.error('获取登记记录失败')
            }
        },
        getStatusTagType(status) {
            return this.statusMap[status]?.type || ''
        },
        getStatusText(status) {
            return this.statusMap[status]?.text || status
        },
        handleEdit(index, row) {
          console.log('点击编辑按钮', row);
          this.editForm = JSON.parse(JSON.stringify(row));
          this.editDialogVisible = true;
          console.log('editDialogVisible:', this.editDialogVisible);
        },
        // 将本地时间格式转换为ISO格式
        convertToISODateTime(localDateTime) {
          if (!localDateTime) return '';

          try {
            // 处理多种可能的时间格式
            let dateStr = localDateTime;
            if (!dateStr.includes('T')) {
              dateStr = dateStr.replace(' ', 'T');
            }
            if (!dateStr.includes('.')) {
              dateStr += ':00.000';
            }
            if (!dateStr.includes('+')) {
              dateStr += '+08:00';
            }

            const date = new Date(dateStr);
            if (isNaN(date.getTime())) {
              console.error('无效的时间格式:', localDateTime);
              return '';
            }

            console.log('转换前:', localDateTime, '转换后:', date.toISOString());
            return date.toISOString();
          } catch (e) {
            console.error('时间转换错误:', e);
            return '';
          }
        },

        async confirmEdit() {
          try {
            // 转换时间格式为后端要求的ISO格式
            const submitData = {
              ...this.editForm,
              aentertime: this.convertToISODateTime(this.editForm.aentertime),
              aouttime: this.convertToISODateTime(this.editForm.aouttime),
              applisubtime: this.convertToISODateTime(this.editForm.applisubtime)
            };

            console.log('提交数据:', submitData);
            const res = await this.$axios.post('/api/manage/applyrecord/update', submitData, {
              headers: {
                'Content-Type': 'application/json'
              }
            });
            console.log('接口响应:', res.data);
            this.$message.success('更新成功');
            this.editDialogVisible = false;
            this.fetchApplyrecordList();
          } catch (error) {
            console.error('更新失败', error);
            if (error.response && error.response.data) {
              this.$message.error(`更新失败: ${error.response.data.message || error.response.data}`);
            } else {
              this.$message.error('更新失败: 请求参数可能有误');
            }
          }
        },
        handleDelete(index, row) {
            console.log(row.anumbering)
          this.$confirm('确认删除这条记录吗?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(async () => {
            try {
              const res = await this.$axios.post('/api/manage/applyrecord/delete',

                  null,
                {
                    params: {anumbering: row.anumbering},
                  //   headers: {
                  //   'Content-Type': 'application/json'
                  // }
                }
              );
              if (res.code === 200) {
                this.$message.success('删除成功');
                this.fetchApplyrecordList();
              } else {
                this.$message.error(res.message || '删除失败');
              }
            } catch (error) {
              console.error('删除失败', error);
              this.$message.error(error.response?.message || '删除失败');
            }
          }).catch(() => {
            this.$message.info('已取消删除');
          });
        },
        handleCurrentChange(val) {
            this.currentPage = val;
        },
        handleSearch() {
          this.currentPage = 1;
        },
        filterToday() {
          const today = new Date();
          const startDate = new Date(today.getFullYear(), today.getMonth(), today.getDate(), 0, 0, 0);
          const endDate = new Date(today.getFullYear(), today.getMonth(), today.getDate(), 23, 59, 59);

          this.dateRange = [
            startDate.toISOString().replace('T', ' ').substring(0, 16),
            endDate.toISOString().replace('T', ' ').substring(0, 16)
          ];

          this.filterForm = {
            name: '',
            phone: '',
            idNumber: '',
            status: ''
          };

          this.currentPage = 1;
        },
        /**
         * 生成Excel报表并下载
         * 1. 准备基础数据：将筛选后的数据转换为报表需要的格式
         * 2. 统计数据：
         *    - 各审批状态的数量统计
         *    - 按小时统计访问人数
         * 3. 创建Excel工作簿并添加两个工作表：
         *    - 入校记录表：包含详细数据
         *    - 统计数据表：包含汇总信息
         * 4. 生成Excel文件并自动下载
         */
        generateReport() {
          // 准备基础数据 - 将对象属性转换为中文表头
          const reportData = this.filteredData.map(item => ({
            姓名: item.aname,
            手机号: item.aphone,
            身份证号: item.aidnumber,
            进校时间: item.aentertime,
            离校时间: item.aouttime,
            审批状态: this.getStatusText(item.astatus),
            入校事由: item.adesc
          }));

          // 统计各状态数量 - 生成{状态:数量}的映射对象
          const statusStats = {};
          this.filteredData.forEach(item => {
            const status = this.getStatusText(item.astatus);
            statusStats[status] = (statusStats[status] || 0) + 1;
          });

          // 按小时统计访问人数 - 初始化24小时数组并填充数据
          const hourlyStats = Array(24).fill(0);
          this.filteredData.forEach(item => {
            const hour = new Date(item.aentertime).getHours();
            hourlyStats[hour]++;
          });

          // 创建Excel工作簿
          const wb = XLSX.utils.book_new();

          // 添加基础数据表 - 使用json_to_sheet转换数据
          const wsData = XLSX.utils.json_to_sheet(reportData);
          XLSX.utils.book_append_sheet(wb, wsData, "入校记录");

          // 添加统计表 - 使用aoa_to_sheet转换二维数组
          const statsData = [
            ["总访问人数", this.filteredData.length],
            ...Object.entries(statusStats).map(([status, count]) => [status, count]),
            ["按小时统计"],
            ...hourlyStats.map((count, hour) => [`${hour}:00-${hour+1}:00`, count])
          ];
          const wsStats = XLSX.utils.aoa_to_sheet(statsData);
          XLSX.utils.book_append_sheet(wb, wsStats, "统计数据");

          // 生成Excel文件并下载
          const wbout = XLSX.write(wb, {
            bookType: 'xlsx',  // 文件类型为xlsx
            type: 'array'       // 输出为数组格式
          });
          saveAs(
            new Blob([wbout], {
              type: "application/octet-stream"  // 设置文件类型
            }),
            `入校记录报表_${new Date().toISOString().slice(0,10)}.xlsx`  // 文件名包含日期
          );
        }
    }
}
</script>

<style scoped>
.box{
    /*margin: 0;*/
    padding: 0;
    /*height: 100%;*/
    width: 100%;
    /*border: 1px solid black;*/
    background-color: white;
    /*margin: 20px;*/
}
.head_one{
    /*margin: 10px;*/
    /*width: 1000px;*/
    padding: 10px;
    /*border: 1px solid black;*/
    display: flex;
    flex-direction: row;
    /*margin: 10px;*/
}
.head_two{
    display: flex;
    margin-left: 30px;
}
.head_two > p{
    width: 50px;
    text-align: left;
    font-size: 12px;
    padding-top: 5px;
}
.head_box{
    display: flex;
    flex-direction: row;
    justify-content: right;
    font-size: 12px;
    margin: 10px;
    /*border: 1px solid black;*/
    /*width: 300px;*/
}
.head_box > p{
    /*border: 1px solid black;*/
    /*text-align: left;*/
    width: 80px;
    text-align: center;
    /*line-height: 50px;*/
    /*padding-top: 1px;*/
}
.el-input{
    height: 35px;
    /*justify-content: right;*/
    /*width: 50px;*/
}
.demo_range{
    margin: 8px;
}
.el-date-editor{
    margin: 8px;
}
.table{
    margin-top: 5vh;
    border-top: 1px solid lightgray;
}
.text{
    /*margin: 10px;*/
    /*border: 1px solid black;*/
    border-bottom: 2px solid lightgray;
}

</style>
