import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class TruthTable{
    public static void main(String[] args) {
        //Declarando tabelas passando como argumento o 'range' em que cada saída é 1
        Map<Integer, Boolean> phTanqueBaixo = generateTwosComplementTable((byte)-128,(byte)-91);
        Map<Integer, Boolean> phCanaletasBaixo = generateTwosComplementTable((byte)-128,(byte)-78);
        Map<Integer, Boolean> phCanaletasAlto = generateTwosComplementTable((byte)-43,(byte)127);
        Map<Integer, Boolean> temperaturaBaixa = generateTwosComplementTable((byte)-128,(byte)25);
        Map<Integer, Boolean> temperaturaAlta = generateTwosComplementTable((byte)63,(byte)127);
        Map<Integer, Boolean> iluminacaoBaixa = generateTable(0,127);
        Map<Integer, Boolean> pressaoBaixa = generateTable(0,51);

        //Armazena as tabelas em arquivos .csv para serem exibidas no Excel
        tableToCsv(phTanqueBaixo, "phTanqueBaixo");
        tableToCsv(phCanaletasBaixo, "phCanaletasBaixo");
        tableToCsv(phCanaletasAlto, "phCanaletasAlto");
        tableToCsv(iluminacaoBaixa, "iluminacaoBaixa");
        tableToCsv(temperaturaBaixa, "temperaturaBaixa");
        tableToCsv(temperaturaAlta, "temperaturaAlta");
        tableToCsv(pressaoBaixa, "pressaoBaixa");
    }

    //Gera tabelas de acordo com 'range', eventos de 0 até 255
    public static Map<Integer, Boolean> generateTable(int min, int max){
        Map<Integer, Boolean> table = new LinkedHashMap<>();
        for(int i=0; i<=255; i++){
            table.put(i, i>=min && i<=max);
            if(i==255)break;
        }
        return table;
    }

    //Gera tabelas de acordo com 'range', eventos de -128 até 127
    public static Map<Integer, Boolean> generateTwosComplementTable(byte min, byte max){
        Map<Integer, Boolean> table = new LinkedHashMap<>();
        for(byte i=-128; i<=127; i++){
            table.put((int)i, i>=min && i<=max);
            if(i==127)break;
        }
        return table;
    }

    //Converte valores inteiros para String em binário
    public static String toBinary(int value){
        return String.format("%8s",Integer.toBinaryString(value & 0xFF)).replace(' ', '0');
    }

    //Converte tabelas verdade em arquivos .CSV para visualização no Excel
    public static void tableToCsv(Map<Integer, Boolean> table, String filePath){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\Temp\\"+filePath+".csv"))){
            bw.write("value,a,b,c,d,e,f,g,h,X");
            table.forEach((event, output)->{
                String line = String.format("%d %s %d", event, toBinary(event).replaceAll(".(?=.)", "$0 "), (output?1:0));
                try{
                    bw.newLine();
                    bw.write(line.replace(' ', ','));
                }catch(IOException ex){ex.printStackTrace();}
            });
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
}