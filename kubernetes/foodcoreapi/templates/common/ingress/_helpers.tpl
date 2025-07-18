{{- define "ingress.name" -}}
{{- .Values.ingress.name | trunc 63 | trimSuffix "-" }}
{{- end }}

{{- define "ingress.fullname" -}}
{{- printf "%s-%s" .Chart.Name .Values.ingress.name | trunc 63 | trimSuffix "-" }}
{{- end }}

{{- define "storageclass.name" -}}
{{- .Values.storageClass.name | trunc 63 | trimSuffix "-" }}
{{- end }}

{{- define "storageclass.fullname" -}}
{{- printf "%s-%s" .Chart.Name .Values.storageClass.name | trunc 63 | trimSuffix "-" }}
{{- end }}

{{- define "ingress.services" -}}
- name: {{ printf "%s-service" (include "api.fullname" .) }}
  port: {{ .Values.api.ports.port }}
  path: {{ .Values.api.ingress.path }}
  pathType: {{ .Values.api.ingress.pathType }}
- name: {{ printf "%s-service" (include "kibana.fullname" .) }}
  port: {{ .Values.kibana.ports.port }}
  path: {{ .Values.kibana.ingress.path }}
  pathType: {{ .Values.kibana.ingress.pathType }}
- name: {{ printf "%s-service" (include "adminer.fullname" .) }}
  port: {{ .Values.adminer.ports.port }}
  path: {{ .Values.adminer.ingress.path }}
  pathType: {{ .Values.adminer.ingress.pathType }}
{{- end }}

