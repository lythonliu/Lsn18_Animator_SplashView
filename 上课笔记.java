
Ricky �޶�
QQ: 576013289

����
-----------------------
һ����ͳ��2D����---���䶯��+֡����
	ʵ�ֵı�����ʲô��canvas���Ƶ�---����任Matrix
�������Զ���
API 3.0�Ժ�
1.���Զ�����ϵͳʹ�ã�
2.����
3.Դ�����
4.���ǰ��һЩ��Ч+���Զ���---�ۺϰ���
//��ҵ��ˢ�ʻ�Ч��

1.ʲô�����Զ��������Զ�����ʲô�ص㣿
	button.setTranslationX(200);
	�ʹ�ͳ���䶯��������---��ı�view��ʵ�����ԡ����磺�ı�button��λ�ã�ƽ�ƺ��״̬���ǿ��Ա������
2.���Զ���
3.0API���µļ��ݷ�����NineOldAndroid.jar
	1��ObjectAnimator
	2��ValueAnimator
	3��PropertyValueHolder
	4��TypeEvaluator ��ֵ��---�ı��㷨--�ı�ֵ
	5��Interpolator ��ֵ��---���Ƽ�����ٶ�

facebook---rebound

==========================================================
1.��ҵ��ˢ�ʻ�Ч��
˼·�����֡� 1.�Ի�ؼ���������ʻ����ձ����������˶�----���ƵĻ�(������SVG or ��С��ͼƬ)��������·������
		Path+PathMeasure ��С���İ�������
	     2.����View�Ķ�����ÿһ���ʻ�����һ��ImageView������ͨ�����Զ������������˶�
	     ObjectAnimator.

���������ߣ����������߷���ʽ
2.��ҵ���Ա�������ת����

================================================
ObjectAnimator.ofFloat(view,"translationX",0f,1f);
����Methodʵ�ֵĵ���setTranslationX(x)
Method.invoke(object, ����);


���Զ���Դ��---��ҵ����NineOldAndroid.jarԴ��ʵ�֡�






================================���Զ���--Դ�������API-22�汾��=========================================

1.ObjectAnimator.ofFloat()-->anim.setFloatValues(values);
2.-->super.setFloatValues(values);
3.-->ValueAnimator415��     valuesHolder.setFloatValues(values);
4.-->PropertyValueHodler608��    mKeyframes = KeyframeSet.ofFloat(values);//����ÿһ֡��KeyFrames����List
PropertyValueHodler��KeyFrameSet���ͨ���ӿ�KeyFrame���
��FloatPropertyValueHodler, FloatKeyFrameSet��

------------start---------
1.ObjectAnimator.start()--->super.start();
2.-->ValueAnimaor.start();
3.----->1045 animationHandler.start();
4.--->scheduleAnimation();
5.-->mChoreographer.postCallback(Choreographer.CALLBACK_ANIMATION, this, null);
	1��this��һ���߳�runnable
	2��Choreographer����һ����ֱͬ����---���ڿ���ÿ��16msˢ�µĿ�����������ͨ�������ʱ���������ϵ��������runnable�̡߳�
6.-->   public void run() {
	    mAnimationScheduled = false;
	    doAnimationFrame(mChoreographer.getFrameTime());
	}
7.--->  for (int i = 0; i < count; ++i) {
	    anim.startAnimation(this);
	}
	//���뵽������Ҫ�������������Ĵ��뻹û��ִ���꣬�����ٿ��������Ķ�����ʼִ�еĴ��룩


8.-->ValueAnimaor.startAnimation()
9.---->1195 initAnimation();//��ʼ������--��PropertyValueHodler�����mSetter������ֵ
	// ���ǵ��õ�ValueAnimator   ������ʵ����ObjectAnimator

10.---> 853  mValues[i].setupSetterAndGetter(target);
PropertyValueHodler  setupSetterOrGetter

749  setterOrGetter = getPropertyFunction(targetClass, prefix, valueType);//����Method���󣬸�mSetter��ֵ
PropertyValueHodler  getPropertyFunction(){
	//���䣬�õ�Method����
}

----------------------����������ʼ����---------------------
����ValueAnimator����anim.doAnimationFrame  715��

--->animationFrame(long currentTime) 1262��

animateValue  1298��  ������д�˸÷���ע��Ҫ������ObjectAnimator{
	for (int i = 0; i < numValues; ++i) {
            mValues[i].setAnimatedValue(target);
        }
}

PropertyValueHodler.setAnimatedValue(){
	mSetter.invoke(target, mTmpValueArray);//PropertyValueHodler�������1166�У����ս����淴���Method������---��������setTranslationX(x)�ȷ����ﵽ�˶�����Ŀ�ġ�
}

=======================���Զ���ʵ��================================
�Զ���ؼ����̳пؼ����Ի�ؼ�����Ͽؼ���
�������棺1.Splash����View��2.������ContentView
SplashView����Ԥ����----СԲ��ת�Ķ���ʱ���ǲ�ȷ���ġ�
SplashView������ContentView����

������
1.СԲ����ת������
2.СԲ���ݺ;ۺ϶�����
3.ˮ���Ƶ���ɢ����

SplashView extends View{
	onDraw(){
		//���ƶ���----����Ϳ�������
		//1.����--���ڰ壬Ϳ�ɰ�ɫ
		//2.����СԲ
	}
}





