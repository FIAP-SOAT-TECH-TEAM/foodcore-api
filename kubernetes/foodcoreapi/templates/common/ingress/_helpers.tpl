{{- define "ingress.name" -}}
{{- .Values.ingress.name | trunc 63 | trimSuffix "-" }}
{{- end }}

{{- define "ingress.fullname" -}}
{{- printf "%s-%s" .Chart.Name .Values.ingress.name | trunc 63 | trimSuffix "-" }}
{{- end }}