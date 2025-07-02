<template>
<!--    当前页面为账号管理——账号列表的vue组件-->
    <div class="box">
<!--        下面的text依旧是标题-->
        <div class="text">
            <p style="font-size: 17px;font-weight: bold;margin-left: 15px;padding-top: 10px">账号列表</p>
        </div>

<!--        顶部筛选组件 start-->
        <div class="head_one">
            <!--            下面的每一个head_box的块级元素都用的flex弹性布局让其里面水平-->
            <div class="head_box">
                <p>姓名</p>
                <el-input  type="text" placeholder="姓名"></el-input>
            </div>
            <div class="head_box">
                <p>手机号</p>
                <el-input  type="text" placeholder="手机号"></el-input>
            </div>
            <div class="head_box">
                <p>身份证号</p>
                <el-input  type="text" placeholder="身份证号"></el-input>
            </div>
            <div class="head_box">
                <p>黑名单</p>
                <el-select v-model="blacklistFilter" placeholder="请选择" style="width: 120px">
                  <el-option label="全部" value=""></el-option>
                  <el-option label="是" value="1"></el-option>
                  <el-option label="否" value="0"></el-option>
                </el-select>
            </div>
        </div>
        <!--        下面的head_two是筛选模块的第二行的内容（选择时间）-->
        <div class="head_two">
            <p>创建时间范围</p>
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
        </div>
<!--        顶部筛选组件 end-->


<!--        下面是数据展示部分-->
        <div class="tableList">
            <el-table :data="paginatedData">
<!--                下面这个column里面设置了一个type为selection，说明这个列前面有勾选框-->
                <el-table-column type="selection" width="55"></el-table-column>
                <el-table-column label="ID编号" prop="uid" width="70"></el-table-column>
                <el-table-column label="姓名" prop="uname" width="80" show-overflow-tooltip>
                  <template #default="scope">
                    <span style="white-space: nowrap; text-overflow: ellipsis; overflow: hidden; display: block;">
                      {{ scope.row.uname }}
                    </span>
                  </template>
                </el-table-column>
                <el-table-column label="密码" prop="pwd" width="80" show-overflow-tooltip>
                    <template #default="scope">
                    <span style="white-space: nowrap; text-overflow: ellipsis; overflow: hidden; display: block;">
                      {{ scope.row.pwd }}
                    </span>
                    </template>
                </el-table-column>
                <el-table-column label="性别" prop="usex" width="60"></el-table-column>
                <el-table-column label="身份证号" prop="uidnumber" width="100" show-overflow-tooltip>
                    <template #default="scope">
                    <span style="white-space: nowrap; text-overflow: ellipsis; overflow: hidden; display: block;">
                      {{ scope.row.uidnumber }}
                    </span>
                    </template>
                </el-table-column>
                <el-table-column label="手机号" prop="uphone" width="120"></el-table-column>
                <el-table-column label="头像" prop="upic"></el-table-column>
                <el-table-column label="是否黑名单" width="95">
                  <template #default="scope">
                    <el-tag v-if="scope.row.blacklist === '1'" type="info" effect="dark">黑名单</el-tag>
                  </template>
                </el-table-column>
                <el-table-column label="创建时间" width="160">
                  <template #default="scope">
                    {{ formatDateTime(scope.row.createtime) }}
                  </template>
                </el-table-column>
                <el-table-column label="操作">
                    <template #default="scope">
                        <el-button size="small" @click="handleEdit(scope.$index, scope.row)"
                        >Edit</el-button
                        >
                        <el-button
                            size="small"
                            type="danger"
                            @click="handleDelete(scope.$index, scope.row)"
                        >Delete</el-button
                        >
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
<!--                这是一个分页组件-->
            <el-pagination
                :current-page="currentPage"
                :page-size="pageSize"
                :page-sizes="[5, 8, 10, 20]"
                layout="total, sizes, prev, pager, next, jumper"
                :total="tableData.length"
                @current-change="handleCurrentChange"
                @size-change="handleSizeChange"
                background
            />
            </div>
            <div class="button">
<!--                俩按钮通过flex布局水平展示-->
                <el-button type="danger">批量删除</el-button>
                <el-button type="primary">取消选择</el-button>
            </div>
        </div>
    </div>

    <!-- 编辑弹窗 -->
    <el-dialog title="编辑账号" v-model="dialogVisible" width="50%">
      <el-form :model="editForm" label-width="100px">
        <el-form-item label="ID编号">
          <el-input v-model="editForm.uid" disabled></el-input>
        </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="editForm.uname"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-select v-model="editForm.usex">
            <el-option label="男" value="男"></el-option>
            <el-option label="女" value="女"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="身份证号">
          <el-input v-model="editForm.uidnumber"></el-input>
        </el-form-item>
        <el-form-item label="手机号">
          <el-input v-model="editForm.uphone"></el-input>
        </el-form-item>
          <el-form-item label="密码">
              <el-input v-model="editForm.pwd"></el-input>
          </el-form-item>
        <el-form-item label="是否黑名单">
          <el-select v-model="editForm.blacklist">
            <el-option label="否" value="0"></el-option>
            <el-option label="是" value="1"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="cancelEdit">取消</el-button>
        <el-button type="primary" @click="submitEdit">提交</el-button>
      </template>
    </el-dialog>
</template>

<script>
export default {
    name: "accountList",

    data() {
        return {
            dialogVisible: false,
            editForm: {},
            blacklistFilter: '',
            currentPage: 1,
            pageSize: 8,
            tableData: []
        }
    },
    created() {
        this.fetchUserList();
    },
    methods: {
        formatDateTime(dateTime) {
            if (!dateTime) return '';
            const date = new Date(dateTime);
            return `${date.getFullYear()}-${(date.getMonth()+1).toString().padStart(2, '0')}-${date.getDate().toString().padStart(2, '0')} ${date.getHours().toString().padStart(2, '0')}:${date.getMinutes().toString().padStart(2, '0')}`;
        },
        async fetchUserList() {
            try {
                const res = await this.$axios.get('/api/manage/user/list');
                // 保持前端参数名不变，后端数据可能需要映射
                this.tableData = res.data.map(item => ({
                    uid: item.uid,
                    uname: item.uname,
                    usex: item.usex,
                    uidnumber: item.uidnumber,
                    uphone: item.uphone,
                    pwd: item.upwd,
                    upic: item.upic,
                    blacklist: item.blacklist ? '1' : '0',
                    createtime: item.createtime
                }));
            } catch (error) {
                console.error('获取用户列表失败:', error);
                this.$message.error('获取用户列表失败');
            }
        },
        handleCurrentChange(val) {
            this.currentPage = val;
        },
        handleSizeChange(val){
            this.pageSize = val;
            this.currentPage = 1;
        },
        async handleEdit(index, row) {
            this.editForm = JSON.parse(JSON.stringify(row));
            this.dialogVisible = true;
        },
        async handleDelete(index, row) {
            try {
                await this.$confirm('确认删除该用户吗?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                });
                const res = await this.$axios.post('/api/manage/user/delete', null, {
                    params: { id: row.uid }
                });
                if (res.code === 200) {
                    this.$message.success('删除成功');
                    this.fetchUserList();
                } else {
                    this.$message.error(res.message);
                }
            } catch (error) {
                if (error !== 'cancel') {
                    this.$message.error('删除失败');
                }
            }
        },
        async submitEdit() {
            try {
                // 转换字段名以匹配后端
                const requestData = {
                    uid: this.editForm.uid,
                    uname: this.editForm.uname,
                    usex: this.editForm.usex,
                    uidnumber: this.editForm.uidnumber,
                    uphone: this.editForm.uphone,
                    upwd: this.editForm.pwd,
                    blacklist: this.editForm.blacklist === '1' ? 1 : 0
                };

                const res = await this.$axios.post('/api/manage/user/update', requestData);

                // 调试日志
                console.log('API响应:', res);
                console.log('响应数据:', res?.code);
                console.log('响应状态:', res?.message);

                // 更健壮的状态检查
                if (res?.code === 200 && res?.code === 200) {
                    this.$message.success(res.message || '更新成功');
                    this.fetchUserList();
                    this.dialogVisible = false;
                } else {
                    const errorMsg = res?.data?.message ||
                                  res?.statusText ||
                                  '更新失败，未知错误';
                    this.$message.error(errorMsg);
                }
            } catch (error) {
                console.error('更新异常:', error);
                const errorMsg = error.response?.data?.message ||
                              error.message ||
                              '更新失败，请检查网络连接';
                this.$message.error(errorMsg);
            }
        },
        cancelEdit() {
            this.dialogVisible = false;
        },
        async handleSearch() {
            try {
                const params = {
                    name: this.form.name,
                    phone: this.form.phone,
                    idNumber: this.form.idNumber,
                    isBlacklisted: this.blacklistFilter,
                    startTime: this.dateRange?.[0],
                    endTime: this.dateRange?.[1]
                };
                const res = await this.$axios.get('/api/manage/user/list', { params });
                this.tableData = res.data.map(item => ({
                    uid: item.uid,
                    uname: item.uname,
                    usex: item.usex,
                    uidnumber: item.uidnumber,
                    uphone: item.uphone,
                    pwd: item.upwd,
                    upic: item.upic,
                    blacklist: item.blacklist ? '1' : '0',
                    createtime: item.createtime
                }));
                this.currentPage = 1;
            } catch (error) {
                console.error('搜索用户失败:', error);
                this.$message.error('搜索用户失败');
            }
        }
    },
    computed: {
        paginatedData() {
            let filteredData = this.tableData;

            if (this.blacklistFilter !== '') {
                filteredData = filteredData.filter(item =>
                    item.blacklist === this.blacklistFilter
                );
            }

            const start = (this.currentPage - 1) * this.pageSize;
            const end = start + this.pageSize;
            return filteredData.slice(start, end);
        }
    }
}
</script>

<style scoped>
.box{
    margin: 0;
    padding: 0;
    /*height: 100%;*/
    width: 100%;
    /*border: 1px solid black;*/
    background-color: white;
    /*margin: 20px;*/
}
.text {
    /*margin: 10px;*/
    /*border: 1px solid black;*/
    border-bottom: 2px solid lightgray;
    /*display: flex;*/
}
.tableList{
    margin-top: 20px;
    margin-left: 20px;
}
.pagination{
    /*margin: 20px;*/
    margin-top: 20px;
    margin-bottom: 20px;
}
.button{
    padding-bottom: 30px;
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
</style>
