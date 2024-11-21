@echo off
kubectl apply -f ../mongo-pv.yaml
kubectl apply -f ../mongodb-pvc.yaml
kubectl apply -f ../mongodb-statefulset.yaml
kubectl apply -f ../mongodb-service.yaml
kubectl apply -f ../app-configmap.yaml
kubectl apply -f ../app-deployment.yaml
kubectl apply -f ../app-service.yaml
kubectl apply -f ../app-hpa.yaml
kubectl apply -f ../metrics.yaml
kubectl apply -f ../role.yaml
kubectl apply -f ../rolebinding.yaml
pause
