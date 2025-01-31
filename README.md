

O projeto "Eventicket" é uma aplicação de gerenciamento de eventos e vendas de ingressos, desenvolvida utilizando a plataforma Spring Boot. O objetivo principal é permitir a criação, atualização, busca e exclusão de eventos, ingressos, usuários, endereços e categorias, oferecendo uma experiência completa para a administração de eventos e a compra de ingressos. 

### **Principais Funcionalidades**

1. **Gerenciamento de Compras de Ingressos (BuyService):**
   - **Salvar Compra:** Permite que um usuário realize a compra de ingressos para um ou mais eventos. O sistema verifica a disponibilidade dos eventos, calcula o total da compra, cria registros de ingressos, e atualiza a quantidade de ingressos disponíveis para os eventos selecionados.
   - **Atualizar Compra:** Permite a atualização dos detalhes de uma compra existente.
   - **Deletar Compra:** Remove uma compra do sistema com base no ID fornecido.
   - **Buscar Todas as Compras:** Retorna uma lista de todas as compras registradas no sistema.
   - **Buscar Compra por ID:** Recupera uma compra específica utilizando o ID da compra.

2. **Gerenciamento de Eventos (EventService):**
   - **Salvar Evento:** Adiciona um novo evento ao sistema.
   - **Atualizar Evento:** Atualiza as informações de um evento existente, incluindo a atualização de endereços vinculados.
   - **Deletar Evento:** Remove um evento do sistema.
   - **Buscar Todos os Eventos:** Retorna uma lista de todos os eventos cadastrados.
   - **Buscar Evento por ID:** Recupera as informações de um evento específico com base no ID fornecido.

3. **Gerenciamento de Usuários (UserService):**
   - **Salvar Usuário:** Permite a criação de um novo usuário no sistema.
   - **Atualizar Usuário:** Atualiza os detalhes de um usuário existente, incluindo suas informações de endereço.
   - **Deletar Usuário:** Remove um usuário do sistema.
   - **Buscar Todos os Usuários:** Retorna uma lista de todos os usuários cadastrados.
   - **Buscar Usuário por ID:** Recupera as informações de um usuário específico com base no ID.
   - **Buscar Eventos na Mesma Cidade:** Retorna eventos que estão na mesma cidade do usuário especificado.

4. **Gerenciamento de Ingressos (TicketService):**
   - **Salvar Ingresso:** Registra um novo ingresso no sistema.
   - **Atualizar Ingresso:** Atualiza as informações de um ingresso existente.
   - **Deletar Ingresso:** Remove um ingresso do sistema.
   - **Buscar Todos os Ingressos:** Retorna uma lista de todos os ingressos disponíveis.
   - **Buscar Ingresso por ID:** Recupera as informações de um ingresso específico.
   - **Alterar Status do Ingresso:** Permite alterar o status de um ingresso para "USADO".

5. **Gerenciamento de Categorias (CategoryService):**
   - **Salvar Categoria:** Permite a criação de uma nova categoria de eventos.
   - **Atualizar Categoria:** Atualiza os detalhes de uma categoria existente.
   - **Deletar Categoria:** Remove uma categoria do sistema.
   - **Buscar Todas as Categorias:** Retorna uma lista de todas as categorias cadastradas.
   - **Buscar Categoria por ID:** Recupera as informações de uma categoria específica.

6. **Gerenciamento de Endereços (AddresService):**
   - **Salvar Endereço:** Adiciona um novo endereço ao sistema.
   - **Atualizar Endereço:** Atualiza as informações de um endereço existente.
   - **Deletar Endereço:** Remove um endereço do sistema.
   - **Buscar Todos os Endereços:** Retorna uma lista de todos os endereços cadastrados.
   - **Buscar Endereço por ID:** Recupera as informações de um endereço específico.

### **Estrutura do Projeto**

O sistema é estruturado em serviços independentes que se comunicam entre si para realizar operações relacionadas a diferentes entidades, como usuários, eventos, ingressos, endereços e categorias. Cada serviço implementa as operações de CRUD (Create, Read, Update, Delete) e possui tratamento de erros para garantir uma experiência robusta e amigável ao usuário.

### **Tecnologias Utilizadas**

- **Java** com **Spring Boot**
- **JPA/Hibernate**
- **PostgreSQL**
- **Maven**

### **Conclusão**

O "Eventicket" é um sistema abrangente para o gerenciamento de eventos e vendas de ingressos, projetado para atender às necessidades de organizadores de eventos, usuários e administradores. Com uma arquitetura modular e extensível, o sistema está preparado para evoluir e se adaptar a novas funcionalidades e demandas do mercado.
