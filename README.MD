##Spring Boot demo

This is Sample spring boot application which uses fabric8-maven-plugin to deploy on Kuberenetes/Openshift.

And this covers below,

    Service Discovery - Kubernetes way
    Circuit Breaker - spring-cloud-hystrix
    Configuration Manangement - configMap and Secrets - Kubernetes way

#####Maven Goal:
```

mvn clean install fabric8:build fabric8:deploy -Dfabric8.mode=openshift -Dfabric8.build.strategy=s2i

```

Please refer spring-boot-demo project