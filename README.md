# palworld-server-manager

> ### :warning: 由于 palworld 官方的 rcon 接口对中文支持不友好 导致最后一位玩家的SteamId加载不完整 所以本程序相当于没什么用


palworld服务端管理工具

## 已实现的功能

- 白名单配置(自动 ban 掉非白名单玩家 解 ban 请查看[官方文档](https://tech.palworldgame.com/server-commands))

## 如何使用

- 安装Java 8
- 修改 `config.json` 配置文件
- 启动 `java -jar palworld-server-manager-1.0-SNAPSHOT.jar`

## 如何配置

```
{
  "serverConfigList": [ # 服务器相关配置
    {
      "serverHost": "127.0.0.1", # 服务器地址
      "serverPort": 25575, # 服务器 rcon 端口
      "serverPassword": "abcd1234",# 服务器 rcon 密码
      "checkCycle": 10000,# 服务器 rcon 查询间隔、单位毫秒(ms)
      "steamIdWhitelist": [# 服务器白名单SteamId
        "765611993XXXXXXXX",
        "765611993XXXXXXXX"
      ]
    }
  ]
}
```