# Decisões de Projeto - Emergency Centers API

## 1. Estrutura Modular
- Separação clara em camadas (controller, service, repository, domain/model, dto, mapper, event, exception, config).
- Uso de MapStruct para mapeamento DTO <-> Model, facilitando manutenção e testes.

## 2. MongoDB
- Escolha pelo Spring Data MongoDB para simplicidade e performance em operações CRUD.

## 3. Eventos e Notificações
- Utilização do ApplicationEventPublisher do Spring para simular notificações (logs) quando centros atingem 100% de ocupação.

## 4. Validação e Erros
- Validação robusta via Bean Validation.
- Tratamento global de erros com @ControllerAdvice e exceções customizadas.
- Uso de DTOs dedicados para respostas de erro (ex: `NegotiationErrorDTO`).

## 5. Testes
- Cobertura unitária focada em regras de negócio críticas (negociação, notificações, validações).

## 6. Pontuação de Recursos
- Pontuações fixas e centralizadas em enum (`ResourceType`).

## 7. Segurança (Opcional)
- Planejado uso de BasicAuth ou token dummy para simulação de autenticação.

## 8. Filtros e Paginação
- Relatórios com filtros dinâmicos e paginação usando Pageable.
- O endpoint de histórico de negociações exige filtro obrigatório por ID de centro e opcional por data/hora.

## 9. Exclusividade da Negociação para Modificação de Recursos
- Recursos dos centros **só podem ser modificados via fluxo de negociação**; não é possível alterar recursos diretamente pelo endpoint de atualização de centro.
- O endpoint de atualização informa explicitamente essa restrição na resposta.

## 10. Mensagens e Documentação
- Todas as mensagens de erro, respostas e comentários no código são padronizadas em **inglês**.
- Swagger/OpenAPI documentado com exemplos realistas e tipos corretos de recursos.

## 11. DTOs Estruturados para Respostas
- Uso de DTOs dedicados para respostas de sucesso (ex: `NegotiationResponseDTO`, `CommunityCenterResponseDTO`).
- Mensagens opcionais são usadas para informar exceções de regra de pontos na negociação.

## 12. Docker e Dev Experience
- Uso de Docker Compose para facilitar setup local e garantir ambiente consistente para todos os desenvolvedores.
- O projeto sobe aplicação e MongoDB juntos, prontos para uso imediato.

## Dúvidas
- Como será a integração futura com outros microsserviços (eventos, mensageria)?
- Regras adicionais para recursos ou tipos customizados?

