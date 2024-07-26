package pcprova;


public class Avaliacao {

    public Avaliacao(){}
    
    public Avaliacao(String[] questoes, String[][] alternativas, String[] respostas, int qtdQuestoes, int qtdAlternativas){
    
        this.questoes = questoes;
        this.alternativas = alternativas;
        this.respostas = respostas;
        this.qtdQuestoes = qtdQuestoes;
        this.qtdAlternativas = qtdAlternativas;
    }

    public void setQuestoes(String[] questoes) {
        this.questoes = questoes;
    }

    public String[] getQuestoes() {
        return questoes;
    }

    public void setAlternativas(String[][] alternativas) {
        this.alternativas = alternativas;
    }

    public String[][] getAlternativas() {
        return alternativas;
    }

    public void setRespostas(String[] respostas) {
        this.respostas = respostas;
    }

    public String[] getRespostas() {
        return respostas;
    }

    public void setQtdQuestoes(int qtdQuestoes) {
        this.qtdQuestoes = qtdQuestoes;
    }

    public int getQtdQuestoes() {
        return qtdQuestoes;
    }

    public void setQtdAlternativas(int qtdAlternativas) {
        this.qtdAlternativas = qtdAlternativas;
    }

    public int getQtdAlternativas() {
        return qtdAlternativas;
    }
    
    private String[] questoes;
    private String[][] alternativas;
    private String[] respostas;
    private int qtdQuestoes;
    private int qtdAlternativas;
}
