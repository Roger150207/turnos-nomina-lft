# Casos de prueba

Cada caso se resolvió **a mano primero** y después se comparó contra el programa
(prueba automática en `CalculadoraSemanalTest`). Salario diario usado: **$520.00**.

**Fórmulas** (supuesto S1 de `reglas.md`):
- Valor de la hora = salario diario ÷ horas de la jornada (diurna 8 h, nocturna 7 h, mixta 7.5 h).
- Hora extra doble = valor de la hora × 2 (art. 66, "100% más").
- Hora extra triple = valor de la hora × 3 (art. 68, "200% más").

| # | Caso | Cálculo a mano | Esperado | Programa | Resultado |
|---|------|----------------|----------|----------|-----------|
| 1 | 2026, diurno. Lun–jue 11 h, vie 8 h | Valor hora $65. Extra: 4 días × 3 h = 12 h. Tope de dobles 2026 = 9 h → 9 dobles (9 × $130 = $1,170) + 3 triples (3 × $195 = $585) | **$1,755.00** | $1,755.00 | ✅ |
| 2 | La misma semana en 2028 | Tope de dobles 2028 = 10 h → 10 dobles ($1,300) + 2 triples ($390) | **$1,690.00** | $1,690.00 | ✅ |
| 3 | Solo lunes de 13 h (2026) | 8 h ordinarias; el art. 66 permite 4 h extra al día → 4 dobles (4 × $130); la hora 13 excede el tope diario (art. 68: máx. 12 h entre ordinarias y extra) y queda como **fuera de tope, requiere revisión** | **$520.00** y 60 min fuera de tope | $520.00 y 60 min | ✅ |
| 4 | Semana sin extras: 5 días de 8 h | Todo es jornada ordinaria; no hay extras ni bono | **$0.00** | $0.00 | ✅ |
| 5 | Turno nocturno 22:00 a 06:00, 30 min de descanso | 480 − 30 = 450 min; jornada NOCTURNA (límite 420 min) → 30 min extra dobles: $520 × 30 × 2 ÷ 420 = $74.29; bono nocturno $150 | **$224.29** | $224.29 | ✅ |
| 6 | Turno mixto 14:00 a 22:00, sin descanso | 2 h de noche (menos de 3 h 30 min, art. 60) → MIXTA (límite 450 min); 480 − 450 = 30 min dobles: $520 × 30 × 2 ÷ 450 = $69.33; bono mixto $75 | **$144.33** | $144.33 | ✅ |
| 7 | Semana que inicia el 2026-03-02 | Es anterior al 1-may-2026 (inicio de la reforma) → el programa debe **rechazarla** | Error de validación | Error de validación | ✅ |
| 8 | Lun–sáb de 10 h (2026) | 2 h extra por día, pero el art. 66 permite extra en máximo 4 días a la semana → 4 días × 2 h = 8 dobles (8 × $130 = $1,040); los días 5 y 6 (4 h) quedan fuera de tope | **$1,040.00** y 240 min fuera de tope | $1,040.00 y 240 min | ✅ |

## Notas
- Los casos 1 y 2 cambian **solo** el año: la diferencia ($65) es una hora que pasa de triple a doble, y prueba que el programa lee las reglas por año.
- El caso 3 depende de la decisión de diseño S5: lo que excede los topes **no se paga ni se oculta**, se muestra aparte para revisión.
- El caso 8 depende de la regla de máximo 4 días a la semana con horas extra (art. 66).
- El importe es solo de **extras + bono**; no incluye el salario ordinario de la semana.
