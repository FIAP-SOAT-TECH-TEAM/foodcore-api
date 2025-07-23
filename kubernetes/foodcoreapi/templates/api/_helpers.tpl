{{- define "api.name" -}}
{{- .Values.api.name | trunc 63 | trimSuffix "-" }}
{{- end }}

{{- define "api.fullname" -}}
{{- printf "%s-%s" .Chart.Name .Values.api.name | trunc 63 | trimSuffix "-" }}
{{- end }}

{{- define "api.jdbc.postgres.url" -}}
{{- printf "jdbc:postgresql://%s-service:%v/%s" (include "postgresql.fullname" .) .Values.postgresql.port .Values.postgresql.auth.database | trunc 63 | trimSuffix "-" }}
{{- end }}

{{- define "api.mercadopago.notification.url" -}}
{{- printf "http://%s%s/payments/webhook" (index .Values.ingress.hosts 0).host .Values.api.ingress.path | quote }}
{{- end }}
