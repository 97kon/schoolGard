<template>
  <div class="settings-container">
    <el-tabs type="border-card">
      <!-- 爽约条件设置 -->
      <el-tab-pane label="爽约设置">
        <el-form :model="noShowForm" label-width="180px">
          <el-form-item label="迟到多少分钟算爽约">
            <el-input-number v-model="noShowForm.late" :min="1" /> 分钟
          </el-form-item>

          <el-form-item label="爽约多少次进黑名单">
            <el-input-number v-model="noShowForm.noshowtimes" :min="1" /> 次
          </el-form-item>

          <el-form-item label="黑名单限制天数">
            <el-input-number v-model="noShowForm.limitdays" :min="1" /> 天
          </el-form-item>

          <el-form-item>
            <el-button type="primary" @click="saveNoShowSettings">保存设置</el-button>
          </el-form-item>
        </el-form>
      </el-tab-pane>

      <!-- 入校时间设置 -->
      <el-tab-pane label="入校时间设置">
        <div class="time-range-picker">
          <el-date-picker
            v-model="timeRange.date"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期">
          </el-date-picker>

          <el-time-picker
            v-model="timeRange.time"
            is-range
            range-separator="至"
            start-placeholder="开始时间"
            end-placeholder="结束时间">
          </el-time-picker>

          <el-input v-model="timeRange.remark" placeholder="备注名称" style="width: 200px; margin-left: 10px;"></el-input>

          <el-button
            type="primary"
            @click="isEditing ? saveEdit() : addTimeRange()"
            style="margin-left: 10px;">
            {{ isEditing ? '保存' : '新增' }}
          </el-button>
          <el-button
            v-if="isEditing"
            @click="cancelEdit"
            style="margin-left: 10px;">
            取消
          </el-button>
        </div>

        <!-- 日历展示 -->
        <div class="calendar-container">
          <el-calendar v-model="calendarDate">
            <template #dateCell="{data}">
              <div class="calendar-date">
                <div>{{ data.day.split('-').slice(2).join('-') }}</div>
                <div v-if="isClosedDate(data.day)" class="closed-badge">不开放</div>
              </div>
            </template>
          </el-calendar>
        </div>

        <!-- 时间范围表格 -->
        <el-table :data="timeRanges" border style="width: 100%; margin-top: 20px;">
          <el-table-column label="日期范围" width="190">
            <template #default="scope">
              {{ formatDate(scope.row.startDate) }} 至 {{ formatDate(scope.row.endDate) }}
            </template>
          </el-table-column>
          <el-table-column label="时间范围" width="190">
            <template #default="scope">
              {{ formatTime(scope.row.startTime) }} 至 {{ formatTime(scope.row.endTime) }}
            </template>
          </el-table-column>
          <el-table-column prop="remark" label="备注"></el-table-column>
          <el-table-column label="操作" width="150">
            <template #default="scope">
              <el-button size="small" @click="editTimeRange(scope.row)">编辑</el-button>
              <el-button size="small" type="danger" @click="deleteTimeRange(scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
export default {
  name: "allSettings",
  created() {
    this.fetchNoShowSettings();
    this.fetchOpentimes();
  },
  data() {
    return {
      // 爽约设置表单
      noShowForm: {
          late: '',
          noshowtimes: '',
          limitdays: ''
      },

      // 时间范围设置
      timeRange: {
        date: [],
        time: [],
        remark: ''
      },

      // 日历当前日期
      calendarDate: new Date(),

      // 存储所有时间范围
      timeRanges: [
          // {
          //     date: [2025/4/17,2025/4/20],
          //     time: ['8:00','8:00'],
          //     remark: '测试demo1'
          // }
      ],

      // 当前编辑状态
      editingIndex: -1,
      isEditing: false
    }
  },
  methods: {
    // 格式化日期
    formatDate(date) {
      return new Date(date).toLocaleDateString()
    },

    // 格式化时间
    formatTime(time) {
      return new Date(time).toLocaleTimeString([], {hour: '2-digit', minute:'2-digit'})
    },

    // 保存爽约设置
    async saveNoShowSettings() {
      try {
        const response = await this.$axios.post('/api/manage/updatesettings', this.noShowForm);
        if (response.code === 200) {
          this.$message.success('爽约设置已保存');
        } else {
          this.$message.error(response.message || '保存失败');
        }
      } catch (error) {
        this.$message.error('保存失败: ' + error.message);
      }
    },

    // 获取爽约设置
    async fetchNoShowSettings() {
      try {
        const response = await this.$axios.get('/api/manage/getsettings');
        if (response.code === 200) {
            // console.log(response.data)
          this.noShowForm = {
            late: response.data.late || 30,
            noshowtimes: response.data.noshowtimes || 3,
            limitdays: response.data.limitdays || 30
          };
        }
      } catch (error) {
        console.error('获取设置失败:', error);
      }
    },

    // 获取禁止时间段数据
    async fetchOpentimes() {
      try {
        const response = await this.$axios.get('/api/manage/getDisabledTimes');
        console.log('时间表数据为：',response.data)
        if (response.code === 200) {
          this.timeRanges = response.data.map(item => {
            const startDate = new Date(item.ostartdatetime);
            const endDate = new Date(item.oenddatetime);
            return {
              startDate: startDate,
              endDate: endDate,
              startTime: startDate,
              endTime: endDate,
              remark: item.oname
            };
          });
        }
      } catch (error) {
        console.error('获取禁止时间段失败:', error);
      }
    },

    // 添加时间范围
    async addTimeRange() {
      if (!this.timeRange.date || !this.timeRange.time || !this.timeRange.remark) {
        this.$message.warning('请填写完整的时间范围和备注');
        return;
      }

      // 合并日期和时间，转换为ISO格式
      const startDateTime = new Date(this.timeRange.date[0]);
      startDateTime.setHours(this.timeRange.time[0].getHours());
      startDateTime.setMinutes(this.timeRange.time[0].getMinutes());

      const endDateTime = new Date(this.timeRange.date[1]);
      endDateTime.setHours(this.timeRange.time[1].getHours());
      endDateTime.setMinutes(this.timeRange.time[1].getMinutes());

      const newRange = {
          ostartdatetime: startDateTime.toISOString(),
          oenddatetime: endDateTime.toISOString(),
          oname: this.timeRange.remark
      };
        console.log('输出的数据为2',newRange)


        try {
        const response = await this.$axios.post('/api/manage/addOpentime', newRange);
        console.log('输出的数据为',newRange)
          console.log('返回的数据为：',response.code)
        if (response.code === 200) {
          this.$message.success('禁止时间段已添加');
          this.fetchOpentimes(); // 刷新数据
          this.resetTimeRange();
        } else {
          this.$message.error(response.message || '添加失败');
        }
      } catch (error) {
        this.$message.error('添加失败: ' + error.message);
      }
    },

    // 重置时间范围表单
    resetTimeRange() {
      this.timeRange = {
        date: [],
        time: [],
        remark: ''
      };
    },

    // 判断日期是否不开放
    isClosedDate(date) {
      return this.timeRanges.some(range => {
        const current = new Date(date);
        const start = new Date(range.startDate);
        const end = new Date(range.endDate);
        return current >= start && current <= end;
      });
    },

    // 编辑时间范围
    editTimeRange(row) {
      const index = this.timeRanges.findIndex(item => item === row);
      if (index !== -1) {
        this.editingIndex = index;
        this.isEditing = true;

        // 回填表单数据
        this.timeRange = {
          date: [new Date(row.startDate), new Date(row.endDate)],
          time: [new Date(row.startTime), new Date(row.endTime)],
          remark: row.remark
        };
      }
    },

    // 保存编辑
    saveEdit() {
      if (!this.timeRange.date || !this.timeRange.time || !this.timeRange.remark) {
        this.$message.warning('请填写完整的时间范围和备注');
        return;
      }

      this.timeRanges[this.editingIndex] = {
        startDate: this.timeRange.date[0],
        endDate: this.timeRange.date[1],
        startTime: this.timeRange.time[0],
        endTime: this.timeRange.time[1],
        remark: this.timeRange.remark
      };
      console.log("时间格式为："+this.timeRanges[0])
      this.cancelEdit();
      this.$message.success('修改成功');
    },

    // 取消编辑
    cancelEdit() {
      this.resetTimeRange();
      this.editingIndex = -1;
      this.isEditing = false;
    },

    // 删除时间范围
    deleteTimeRange(row) {
      this.$confirm('确定删除这个时间范围吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const index = this.timeRanges.findIndex(item => item === row);
        if (index !== -1) {
          this.timeRanges.splice(index, 1);
          this.$message.success('删除成功');
        }
      });
    }
  }
}
</script>

<style scoped>
.settings-container {
  padding: 20px;
}

.time-range-picker {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}

.calendar-container {
  margin-bottom: 20px;
}

.calendar-date {
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.closed-badge {
  color: #f56c6c;
  font-size: 12px;
}
</style>
