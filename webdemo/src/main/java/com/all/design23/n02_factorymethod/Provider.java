package com.all.design23.n02_factorymethod;

import com.all.design23.n01_factory.Sender;

/**
 * 工厂方法模式 创建一个工厂接口和创建多个工厂实现类，这样一旦需要增加新的功能，直接增加新的工厂类就可以了，不需要修改之前的代码。
 *
 * /**
 *  * 工厂方法模式：
 *  * 一个抽象产品类，可以派生出多个具体产品类。
 *  * 一个抽象工厂类，可以派生出多个具体工厂类。
 *  * 每个具体工厂类只能创建一个具体产品类的实例。
 *  *
 *  * 抽象工厂模式：
 *  * 多个抽象产品类，每个抽象产品类可以派生出多个具体产品类。
 *  * 一个抽象工厂类，可以派生出多个具体工厂类。
 *  * 每个具体工厂类可以创建多个具体产品类的实例，也就是创建的是一个产品线下的多个产品。
 *  *
 *  * 区别：
 *  * 工厂方法模式只有一个抽象产品类，而抽象工厂模式有多个。
 *  * 工厂方法模式的具体工厂类只能创建一个具体产品类的实例，而抽象工厂模式可以创建多个。
 *  * 工厂方法创建 "一种" 产品，他的着重点在于"怎么创建"，也就是说如果你开发，你的大量代码很可能围绕着这种产品的构造，初始化这些细节上面。也因为如此，类似的产品之间有很多可以复用的特征，所以会和模版方法相随。
 *  *
 *  * 抽象工厂需要创建一些列产品，着重点在于"创建哪些"产品上，也就是说，如果你开发，你的主要任务是划分不同差异的产品线，并且尽量保持每条产品线接口一致，从而可以从同一个抽象工厂继承。
 *
 *  所以说抽象工厂就像工厂，而工厂方法则像是工厂的一种产品生产线
 */
public interface Provider {
    Sender produce();
}
