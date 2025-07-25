{{- define "postgresql.name" -}}
{{- .Values.postgresql.name | trunc 63 | trimSuffix "-" }}
{{- end }}

{{- define "postgresql.fullname" -}}
{{- printf "%s-%s" .Chart.Name .Values.postgresql.name | trunc 63 | trimSuffix "-" }}
{{- end }}