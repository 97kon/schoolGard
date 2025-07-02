<template>
	<view class="container">
		<text>车牌：{{ model.plateNumber }}</text>

		<tn-form :model="model" ref="carForm">
			<!-- 车牌号输入项 -->
			<tn-form-item label="车牌号" prop="plateNumber" :labelPosition="labelPosition" :labelAlign="labelAlign">
				<!-- 输入框 -->
				<view class="plate-input" @click="showPlateKeyboardMethod">
					<view class="plate-input__container">
						<!-- 遍历 plateNumber 数组并显示每个字符 -->
						<view v-for="(char, index) in model.plateNumber" :key="index" class="plate-input__item" :class="{
              'plate-input__item--new-energy': index === 7,
              'plate-input__item--active': index === model.plateNumber.length
            }">
							{{ char || '' }}  <!-- 显示字符，如果为空则显示空字符串 -->
						</view>
					</view>

					<!-- 删除按钮 -->
					<view class="delete-btn" @click="deleteLastCharacter" v-if="model.plateNumber.length > 0">
						删除
					</view>

					<!-- 占位符 -->
					<view class="plate-input__placeholder" v-if="!model.plateNumber" @click="showPlateKeyboard = true">
						点击输入车牌号
					</view>
				</view>

				<!-- 图鸟车牌键盘 -->
				<tn-car-keyboard mode="car" v-model="model.plateNumber" v-if="showPlateKeyboard" @change="handlePlateChange" @close="showPlateKeyboard = false" />
			</tn-form-item>

			<!-- 提交按钮 -->
			<view class="submit-btn">
				<tn-button type="success" shape="round" @click="submitForm">提交</tn-button>
			</view>
		</tn-form>
	</view>
</template>

<script>
export default {
	data() {
		return {
			model: {
				plateNumber: '',  // 初始化为空字符串
			},
			showPlateKeyboard: false,
			labelPosition: 'left',
			labelAlign: 'left'
		};
	},
	methods: {
		showPlateKeyboardMethod() {
			this.showPlateKeyboard = true;
			console.log("点击了输入框");
		},
		handlePlateChange(val) {
			console.log(val);  // 查看输入的值
			this.model.plateNumber += val;  // 将新输入的值追加到车牌号后面
			console.log(this.model.plateNumber);  // 查看更新后的车牌号
		},
		deleteLastCharacter() {
			if (this.model.plateNumber.length > 0) {
				this.model.plateNumber = this.model.plateNumber.slice(0, -1); // 删除最后一个字符
			}
		},
		submitForm() {
			if (!this.model.plateNumber || this.model.plateNumber.length < 7) {
				uni.showToast({
					title: '请输入完整车牌号',
					icon: 'none'
				});
				return;
			}
			uni.showToast({
				title: `车牌号为：${this.model.plateNumber}`,
				icon: 'success'
			});
		}
	}
};
</script>

<style scoped>
.container {
	padding: 30rpx;
}

.plate-input {
	position: relative;
	padding: 20rpx 0;
}

.plate-input__container {
	display: flex;
	flex-wrap: nowrap;
}

.plate-input__item {
	width: 70rpx;
	height: 70rpx;
	margin-right: 10rpx;
	text-align: center;
	line-height: 70rpx;
	background-color: #f2f2f2;
	font-size: 36rpx;
	border-radius: 8rpx;
	border: 1rpx solid #ccc;
}

.plate-input__item--new-energy {
	background-color: #d0f0d0;
}

.plate-input__item--active {
	border: 2rpx solid #00aaee;
}

.plate-input__placeholder {
	position: absolute;
	left: 0;
	top: 0;
	bottom: 0;
	color: #999;
	font-size: 28rpx;
	display: flex;
	align-items: center;
	padding-left: 10rpx;
}

.submit-btn {
	margin-top: 60rpx;
}

.delete-btn {
	position: absolute;
	right: 10rpx;
	top: 50%;
	transform: translateY(-50%);
	background-color: #f5f5f5;
	border-radius: 8rpx;
	padding: 10rpx;
	font-size: 24rpx;
	cursor: pointer;
	border: 1rpx solid #ccc;
}
</style>
