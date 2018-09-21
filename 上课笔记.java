
Ricky 罗鼎
QQ: 576013289

动画
-----------------------
一、传统的2D动画---补间动画+帧动画
	实现的本质是什么？canvas绘制的---矩阵变换Matrix
二、属性动画
API 3.0以后。
1.属性动画的系统使用；
2.案例
3.源码解析
4.结合前面一些特效+属性动画---综合案例
//作业：刷鲜花效果

1.什么是属性动画？属性动画有什么特点？
	button.setTranslationX(200);
	和传统补间动画的区别：---会改变view的实际属性。比如：改变button的位置，平移后的状态还是可以被点击的
2.属性动画
3.0API以下的兼容方案：NineOldAndroid.jar
	1）ObjectAnimator
	2）ValueAnimator
	3）PropertyValueHolder
	4）TypeEvaluator 估值器---改变算法--改变值
	5）Interpolator 插值器---控制计算的速度

facebook---rebound

==========================================================
1.作业：刷鲜花效果
思路：两种。 1.自绘控件，如何让鲜花按照贝塞尔曲线运动----绘制的花(可以是SVG or 很小的图片)按照曲线路径绘制
		Path+PathMeasure （小船的案例。）
	     2.控制View的动画，每一朵鲜花都是一个ImageView，可以通过属性动画控制曲线运动
	     ObjectAnimator.

贝塞尔曲线：贝塞尔曲线方程式
2.作业：淘宝购物旋转动画

================================================
ObjectAnimator.ofFloat(view,"translationX",0f,1f);
反射Method实现的调用setTranslationX(x)
Method.invoke(object, 参数);


属性动画源码---作业：看NineOldAndroid.jar源码实现。






================================属性动画--源码分析（API-22版本）=========================================

1.ObjectAnimator.ofFloat()-->anim.setFloatValues(values);
2.-->super.setFloatValues(values);
3.-->ValueAnimator415行     valuesHolder.setFloatValues(values);
4.-->PropertyValueHodler608行    mKeyframes = KeyframeSet.ofFloat(values);//解析每一帧到KeyFrames集合List
PropertyValueHodler和KeyFrameSet解耦（通过接口KeyFrame解耦）
（FloatPropertyValueHodler, FloatKeyFrameSet）

------------start---------
1.ObjectAnimator.start()--->super.start();
2.-->ValueAnimaor.start();
3.----->1045 animationHandler.start();
4.--->scheduleAnimation();
5.-->mChoreographer.postCallback(Choreographer.CALLBACK_ANIMATION, this, null);
	1）this是一个线程runnable
	2）Choreographer类是一个垂直同步类---用于控制每隔16ms刷新的控制器，可以通过这个定时控制器不断调用上面的runnable线程。
6.-->   public void run() {
	    mAnimationScheduled = false;
	    doAnimationFrame(mChoreographer.getFrameTime());
	}
7.--->  for (int i = 0; i < count; ++i) {
	    anim.startAnimation(this);
	}
	//代码到这里需要深入分析，后面的代码还没有执行完，回来再看（真正的动画开始执行的代码）


8.-->ValueAnimaor.startAnimation()
9.---->1195 initAnimation();//初始化动画--给PropertyValueHodler里面的mSetter变量赋值
	// 不是调用的ValueAnimator   而是真实类型ObjectAnimator

10.---> 853  mValues[i].setupSetterAndGetter(target);
PropertyValueHodler  setupSetterOrGetter

749  setterOrGetter = getPropertyFunction(targetClass, prefix, valueType);//返回Method对象，给mSetter赋值
PropertyValueHodler  getPropertyFunction(){
	//反射，得到Method返回
}

----------------------动画真正开始代码---------------------
接着ValueAnimator里面anim.doAnimationFrame  715行

--->animationFrame(long currentTime) 1262行

animateValue  1298行  子类重写了该方法注意要看子类ObjectAnimator{
	for (int i = 0; i < numValues; ++i) {
            mValues[i].setAnimatedValue(target);
        }
}

PropertyValueHodler.setAnimatedValue(){
	mSetter.invoke(target, mTmpValueArray);//PropertyValueHodler类里面第1166行，最终将上面反射的Method调用了---即调用了setTranslationX(x)等方法达到了动画的目的。
}

=======================属性动画实例================================
自定义控件：继承控件；自绘控件；组合控件；
两个界面：1.Splash界面View；2.主界面ContentView
SplashView用于预加载----小圆旋转的动画时间是不确定的。
SplashView盖在了ContentView上面

动画：
1.小圆的旋转动画；
2.小圆逃逸和聚合动画；
3.水波纹的扩散动画

SplashView extends View{
	onDraw(){
		//绘制动画----计算和控制坐标
		//1.背景--擦黑板，涂成白色
		//2.绘制小圆
	}
}





