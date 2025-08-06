# 📚 Sistema de Checagem de Carga Horária

## 🎯 Sobre o Projeto

Este sistema foi desenvolvido com o objetivo de automatizar e simplificar o cálculo de carga horária acadêmica, nascendo da experiência prática como **membro Dicente titular do PDI (Plano de Desenvolvimento Institucional)** do campus.

Durante as atividades no PDI, percebi que o processo manual de calcular cargas horárias era repetitivo e propenso a erros - sempre com calculadora na mão e papéis espalhados pela mesa. Foi então que decidi criar esta ferramenta para otimizar esse trabalho e reduzir significativamente o tempo gasto em cálculos manuais.

## ✨ Funcionalidades

- **Cadastro de Cursos**: Interface intuitiva para inserir informações dos cursos
- **Cálculo Individual**: Carga horária específica de cada curso cadastrado
- **Agrupamento por Tipo**: Soma automática da carga horária por categoria (MAT, ELO, MEC, etc.)
- **Total Semestral**: Cálculo consolidado de toda a carga horária do semestre
- **Interface Amigável**: Sistema de linha de comando simples e direto

## 🔧 Tecnologias Utilizadas

- **Java**: Linguagem principal do projeto
- **Collections Framework**: ArrayList e HashMap para gerenciamento de dados
- **Programação Orientada a Objetos**: Estrutura modular com separação de responsabilidades

## 📋 Como Usar

1. **Execute o programa**:
   ```bash
   javac Main.java
   java Main
   ```

2. **Adicione seus cursos**:
   - Nome do curso
   - Tipo/categoria (MAT, ELO, MEC, etc.)
   - Quantidade de aulas semanais
   - Número de semanas letivas

3. **Visualize os resultados**:
   - Carga horária individual de cada curso
   - Total por tipo de curso
   - Carga horária total do semestre

## 📊 Exemplo de Saída

```
=== Carga Horária Individual ===
Cálculo I: 66.66 hours

Física I: 50.0 hours

=== Carga Horária por Tipos ===
MAT: 66.66 horas
FIS: 50.0 horas

=== Carga Horária Total do Semestre ===
Carga Horária Total: 116.66 horas
```

## 🏗️ Estrutura do Projeto

```
src/
├── Main.java                 # Classe principal com interface do usuário
├── model/
│   └── Course.java          # Modelo de dados do curso
└── service/
    └── LoadCalculator.java  # Lógica de cálculo de carga horária
```

## 💡 Motivação

Como membro do PDI, enfrentava constantemente a necessidade de calcular e validar cargas horárias para diferentes cursos e modalidades. O processo manual era:

- ⏰ **Demorado**: Cada cálculo levava tempo considerável
- 📝 **Propenso a erros**: Cálculos manuais sempre têm margem para equívocos  
- 🔄 **Repetitivo**: Mesmas operações matemáticas constantemente
- 📊 **Difícil de organizar**: Papéis e anotações espalhadas

Este sistema resolve todos esses problemas, proporcionando:

- ⚡ **Agilidade**: Cálculos instantâneos
- 🎯 **Precisão**: Eliminação de erros humanos
- 🗂️ **Organização**: Dados estruturados e categorizados
- 📈 **Produtividade**: Mais tempo para análises estratégicas

## 🚀 Melhorias Futuras

- [ ] Possibilidade de adicionar mais Semestres
- [ ] Interface gráfica (GUI)
- [ ] Exportação para Excel/PDF
- [ ] Banco de dados para persistência
- [ ] Validação de dados mais robusta
- [ ] Relatórios detalhados
- [ ] Configuração de diferentes fórmulas de cálculo

## 📞 Contato

Desenvolvido por um estudante comprometido com a melhoria dos processos acadêmicos através da tecnologia.

---

*"A tecnologia deve servir para simplificar nossa vida, não complicá-la."* 🎓
