
# 高仿喜马拉雅 音乐播放器 在线下载 支持多线程下载

版本:1.0


	
	高仿喜马拉雅
	项目中使用多进程通讯aidl 播放器采用单独进程:play
	使用tabLayout滑动指示器，fragment预加载
	播放器采用Exoplayer播放 
	多线程下载的项目，使用线程池(Executors.newFixedThreadPool(2))对线程的管理
	支持断点续传，第一个版本默认可以两个线程同时下载
	
版本:1.1.0

	优化项目已存在的问题
	添加广播详情页面,用到了ActivityOptionsCompat共享元素动画
	fragment中跳转使用共享元素动画　 


仿喜马拉雅 效果图

多线程下载 效果图

      
![image](https://github.com/qianxiangsen521/Multi-threadedDownload/blob/master/gif/image.png)

Activity切换 效果图
<div>
<img src="https://github.com/qianxiangsen521/Multi-threadedDownload/blob/master/gif/open.gif" width="480px" height="720px"/>
</div>

用法
	
   您需要将这些权限添加在您的AndroidManifest.xml文件中 6.0以上需要动态申请权限

	<uses-permission android:name="android.permission.INTERNET" />
	<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>

创建实例及配置

  1.创建DownloadMessage实例


	DownloadMessage downlaod = DownloadMessage.init(this);

  

  2.参数配置 需要一个Task对象。

  添加任务 


	Task task = downlaod.addTask(Task task,DownloadUiListener)

	Task task1 = new Task();
	task1.setName("下载1");
	task1.setIamgeUrl(Constants.IMG);
	task1.setId(1);
	task1.setmUniquely_id("102");
	task1.setUrl(Constants.URL1);
 


  2.1第二参数下载回掉默认是ui线程 提供了三个回掉方法

  开始下载
  
	 public void UiStrat()
 
  
  下载的进度

  	 public void UiProgress(Task task,long TotalSize ,int downloadSize)
  
  
  下载结束

	 public void UiFinish(Task task)

  

  3. 开始下载 

	  downlaod.startDownload(Task task);

  4.暂停下载

	  downlaod.pauseDownload(Task task);

  5删除下载

  	  downlaod.delete(Task task,boolean deleteTaskFile)


	