import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class Principal {
    public static void main(String[] args) {

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DecimalFormat numberFormatter = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(Locale.forLanguageTag("pt-BR")));

        List<Funcionario> funcionarios = new ArrayList<>();

        // 3.1 – Inserir todos os funcionários
        funcionarios.add(new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"));
        funcionarios.add(new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"));
        funcionarios.add(new Funcionario("Caio", LocalDate.of(1961, 5, 02), new BigDecimal("9836.14"), "Coordenador"));
        funcionarios.add(new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"));
        funcionarios.add(new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"));
        funcionarios.add(new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"));
        funcionarios.add(new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"));
        funcionarios.add(new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"));
        funcionarios.add(new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletrecista"));
        funcionarios.add(new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente"));


        // 3.2 – Remove João da lista
        funcionarios.removeIf(f -> "João".equalsIgnoreCase(f.getNome()));


        // 3.3 – Imprimir todos os funcionários com todas suas informações
        System.out.println("=== 3.3 - Lista de Funcionários ===");
        for (Funcionario func : funcionarios) {
            System.out.println(formatarFuncionario(func, dateFormatter, numberFormatter));
        }


        // 3.4 – Os funcionários receberam 10% de aumento de salário
        for (Funcionario func : funcionarios) {
            BigDecimal aumento = func.getSalario().multiply(new BigDecimal("0.10"));
            func.setSalario(func.getSalario().add(aumento));
        }


        // 3.5 – Agrupar os funcionários por função em um MAP
        Map<String, List<Funcionario>> funcionariosPorFuncao = funcionarios.stream().collect(Collectors.groupingBy(Funcionario::getFuncao));



        // 3.6 – Imprimir os funcionários, agrupados por função
        System.out.println("\n=== 3.6 - Funcionários Agrupados por Função ===");
        funcionariosPorFuncao.forEach((funcao, lista) -> {
            System.out.println("Função: " + funcao);
            lista.forEach(f -> System.out.println("  - " + formatarFuncionario(f, dateFormatter, numberFormatter)));
        });



        // 3.8 – Imprimir os funcionários que fazem aniversário no mês 10 e 12
        System.out.println("\n=== 3.8 - Aniversariantes dos Meses 10 e 12 ===");
        funcionarios.stream()
                .filter(func -> {
                    int mes = func.getDataNascimento().getMonthValue();
                    return mes == 10 || mes == 12;
                })
                .forEach(func -> System.out.println(formatarFuncionario(func, dateFormatter, numberFormatter)
        ));



        // 3.9 – Imprimir o funcionário com a maior idade (nome e idade)
        System.out.println("\n=== 3.9 - Funcionário com Maior Idade ===");
        if (!funcionarios.isEmpty()) {
            Funcionario maisVelho = Collections.min(funcionarios, Comparator.comparing(Pessoa::getDataNascimento));
            int idade = Period.between(maisVelho.getDataNascimento(), LocalDate.now()).getYears();
            System.out.println("Nome: " + maisVelho.getNome() + ", Idade: " + idade + " anos");
        }


   
        // 3.10 – Imprimir a lista de funcionários por ordem alfabética
        System.out.println("\n=== 3.10 - Funcionários em Ordem Alfabética ===");
        List<Funcionario> funcionariosOrdemAlfabetica = new ArrayList<>(funcionarios);

        funcionariosOrdemAlfabetica.sort(Comparator.comparing(Pessoa::getNome));

        for (Funcionario func : funcionarios) {
            System.out.println(formatarFuncionario(func, dateFormatter, numberFormatter));
        }



        // 3.11 – Imprimir o total dos salários dos funcionários
        System.out.println("\n=== 3.11 - Total dos Salários ===");
        BigDecimal totalSalarios = funcionarios.stream().map(Funcionario::getSalario).reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("Total: R$ " + numberFormatter.format(totalSalarios));



        // 3.12 – Imprimir quantos salários mínimos ganha cada funcionário
        System.out.println("\n=== 3.12 - Quantidade de Salários Mínimos por Funcionário ===");
        BigDecimal salarioMinimo = new BigDecimal("1212.00");

        for (Funcionario func : funcionarios) {
            BigDecimal qtdSalariosMinimos = func.getSalario().divide(salarioMinimo, 2, RoundingMode.HALF_UP);
            System.out.println(func.getNome() + " ganha " + numberFormatter.format(qtdSalariosMinimos) + " salários mínimos.");
        }
    }

        private static String formatarFuncionario(Funcionario func, DateTimeFormatter dateFormatter, DecimalFormat numberFormatter) {
        return String.format("Nome: %-10s | Data Nasc: %s | Salário: R$ %10s | Função: %s",
                func.getNome(),
                func.getDataNascimento().format(dateFormatter),
                numberFormatter.format(func.getSalario()),
                func.getFuncao());
    }
}
