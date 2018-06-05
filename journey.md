# Journey

## Create and publish docker image

Create Dockerfile (see sibling file).

```
from openjdk:8-jre-alpine
add build/libs/k8s-0.0.1-SNAPSHOT.jar /app.jar
entrypoint ["java", "-jar", "/app.jar"]
```

Create the *image* with the tag `k8s-spring-start`.

```
docker build -t k8s-spring-start .
```

Create and run *container* `k8s-spring-start-container` based on the image `k8s-spring-start`, mapping port 8081
on the host to port 8080 on the container.

```
docker run --name k8s-spring-start-container -p 8081:8080 -d k8s-spring-start
```

Run `docker ps` to check the container was created and is running

Run `curl http://localhost:8081/hello` to check the HTTP service is running. 

Run `gcloud auth configure-docker` to configure the `gcloud` credential helper on the docker`` CLI

Run `docker tag k8s-spring-start eu.gcr.io/isel-k8s-0/k8s-spring-start:v0.1.0` to create a tag usable with the GCP
container registry

Run `docker push eu.gcr.io/isel-k8s-0/k8s-spring-start:v0.1.0` to push the image into the GCP container registry.


## Running the application

Run `kubectl run k8s-spring-start --image=eu.gcr.io/isel-k8s-0/k8s-spring-start:v0.1.0 --port=8080 --generator=run/v1`.
This creates a *replication controller* that (how?) creates a pod.

To expose the service to the outside, we run `kubectl expose rc k8s-spring-start --type=LoadBalancer --name k8s-spring-start`.
This creates a service of type *load balancer* that can be inspected by running `kubectl get services`.

On GKE, this *load balander* service is done by:
* Creating an external IP.
* Creating a TCP load balancer exposing the external IP and port 8080 associated with an instance group composed by all
the cluster nodes.

## Creating a pod via a manual definition file

Create a YAML file with the following (see `k8s` folder)

```
apiVersion: v1
kind: Pod
metadata:
  name: manual-0
spec:
  containers:
  - image: eu.gcr.io/isel-k8s-0/k8s-spring-start:v0.2.0
    name: k8s-spring-start
    ports:
    - containerPort: 8080
      protocol: TCP
```

Run `kubectl create -f k8s/pod-manual-0.yaml`

Run `kubectl logs manual-0` to observe the logs for the pod.

Run `kubectl port-forward manual-0 8081:8080` to forward port 8081 of the **local machine** to the pod 8080 port.

Run `kubectl label po manual-0 creation_method=manual` to set a label on an existing pod.

Run `kubectl get po -L creation_method` to get information about pods including the `creation_method` label.

Run `kubectl get po -l creation_method=manual` to filter the pods by a label.

Run `kubectl get ns` to view all the existing namespaces.

Run `kubectl get po  -n kube-system` to filter the pods by a namespace.

Run `kubectl delete po manual-0` to delete a pod.

Run `kubectl delete po -l creation_method=manual` to remove all pods with a given label.

##
