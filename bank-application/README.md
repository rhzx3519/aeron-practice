Application模块主要包含Application Service和一些相关的类。Application模块依赖Domain模块。还是不依赖任何框架，纯POJO。

常用的ApplicationService“套路”
我们可以看出来，ApplicationService的代码通常有类似的结构：AppService通常不做任何决策（Precondition除外），仅仅是把所有决策交给DomainService或Entity，把跟外部交互的交给Infrastructure接口，如Repository或防腐层。

一般的“套路”如下：

准备数据：包括从外部服务或持久化源取出相对应的Entity、VO以及外部服务返回的DTO。
执行操作：包括新对象的创建、赋值，以及调用领域对象的方法对其进行操作。需要注意的是这个时候通常都是纯内存操作，非持久化。
持久化：将操作结果持久化，或操作外部系统产生相应的影响，包括发消息等异步操作。


如果涉及到对多个外部系统（包括自身的DB）都有变更的情况，这个时候通常处在“分布式事务”的场景里，无论是用分布式TX、TCC、还是Saga模式，取决于具体场景的设计，在此处暂时略过。


1. 构建领域边界：ApplicationService的入参是CQE对象，出参是DTO，这些基本上都属于简单的POJO，来确保Application层的内外互相不影响。
2. 降低规则依赖：Entity里面通常会包含业务规则，如果ApplicationService返回Entity，则会导致调用方直接依赖业务规则。如果内部规则变更可能直接影响到外部。
3. 通过DTO组合降低成本：Entity是有限的，DTO可以是多个Entity、VO的自由组合，一次性封装成复杂DTO，或者有选择的抽取部分参数封装成DTO可以降低对外的成本。