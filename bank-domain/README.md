Domain 模块是核心业务逻辑的集中地，包含有状态的Entity、领域服务Domain Service、以及各种外部依赖的接口类
（如Repository、ACL、中间件等。 Domain模块仅依赖Types模块，也是纯 POJO 。