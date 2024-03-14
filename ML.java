import java.util.ArrayList;

/*
 * To Do
 * Add bias
 * 
 */

public class ML {
    
    boolean initBiasState = false; // initializes all bias to 0
    double[] input;
    int[] shape;
    ArrayList<double[][]> net = new ArrayList<>();
    ArrayList<double[]> bias = new ArrayList<>();
    ArrayList<double[]> neurons = new ArrayList<>();
    double growthRate = .1;
    double sigw = 1;

    public ML(int[] shape, ArrayList<double[][]> net, ArrayList<double[]> bias) {
        this.shape = shape;
        this.net = net;
        this.bias = bias;
    }

    // MAKE NEW NEURAL NET
    
    public ML(int[] shape, boolean randBias) {
        this.shape = shape;
        this.initBiasState = randBias;

        for(int i = 0; i < shape.length - +1; i++) {
            addLayer(shape[i + 1], shape[i], initBiasState);
        }
    }

    public void setGrowthRate(double growthRate) {

        this.growthRate = growthRate;

    }

    public void initRandWeight(double[][] w_l_l) {
        for(int i = 0; i < w_l_l.length; i++) {
            for(int n = 0; n < w_l_l[i].length; n++) {
                double value = Math.random();
                w_l_l[i][n] = value * 2 - 1;
            }
        }
    }

    public void initRandWeightConstricted(double[][] w_l_l) {
        for(int i = 0; i < w_l_l.length; i++) {
            for(int n = 0; n < w_l_l[i].length; n++) {
                w_l_l[i][n] = Math.abs(w_l_l[i][n] + (Math.random() - Math.random()) * growthRate);
            }
        }
    }

    //Yes this is still redundant but I'd like to make the distinction
    public void initZeroBias(double[] bias) {
        for(int i = 0; i < bias.length; i++) {
            double value = Math.random();
            bias[i] = value * 2 - 1;
        }
    }

    public void initRandBias(double[] bias) {
        for(int i = 0; i < bias.length; i++) {
            double value = Math.random();
            bias[i] = value * 2 - 1;
        }
    }

    public void addLayer(int x, int y, boolean randBias) {
        double[][] phold = new double[x][y];
        initRandWeight(phold);
        net.add(phold);

        if(randBias) {
            double[] bias = new double[x];
            initRandBias(bias);
            this.bias.add(bias);
        } else {
            double[] bias = new double[x];
            initZeroBias(bias);
            this.bias.add(bias);
        }
    }

    public void modNet() {
        for(int i = 0; i < net.size(); i++) {
            initRandWeightConstricted(net.get(i));
        }
    }
     
    // NET FUNCTIONS

    public double sigmoid(double input/*, double biasSingle*/) {
        return (1 / (1 + Math.exp(-1 * sigw * input)))/* + biasSingle*/;
    }

    public double[] multWeight(double[][] weight, double[] input) {
        double[] phold = new double[weight.length];
        
        for(int i = 0; i < weight.length; i++) {
            for(int n = 0; n < weight[i].length; n++) {
                phold[i] += weight[i][n] * input[n];
            }
        }

        return phold;
    }

    // hi future me, yes i do know that this is redundant. thank you and goodbye - Mil 1:07AM 8/13/2022
    public double[] sigMod(double[] result/*, double[] biasLayer*/) {
        double[] phold = new double[result.length];

        for(int i = 0; i < result.length; i++) {
            phold[i] = sigmoid(result[i]/*, biasLayer[i]*/);
        }

        return phold;
    }

    public double[] fProp(double[] input) {
        for(int i = 0; i < net.size(); i++) {
            neurons.add(sigMod(multWeight(net.get(i), input)/*, bias.get(i)*/)); //put bias back in later
            input = neurons.get(i);
            System.out.println("hi");
        }

        return neurons.get(neurons.size() - 1);
    }

    public ArrayList<double[][]> transposeNet() {
        ArrayList<double[][]> phold = new ArrayList<>();
        for(int i = 0; i < net.size(); i++) {
            phold.add(new double[net.get(i)[0].length][net.get(i).length]);
            for(int col = 0; col < net.get(i)[0].length; col++) {
                for(int row = 0; row < net.get(i).length; row++) {
                    phold.get(i)[col][row] = net.get(i)[row][col];
                }
            }
        }
        return phold;
    }

    // GETTERS

    public String getNetStr() {
        String netString = "";
        for(double[][] hl : net) {
            for(double[] l : hl) {
                for(double n : l) {
                    netString += n + " ";
                }
                netString += "\n";
            }
            netString += "\n";
        }

        return netString;

    }

    public String getNetStrTranspose() {
        String phold = "";
        for(int i = 0; i < net.size(); i++) {
            for(int col = 0; col < net.get(i)[0].length; col++) {
                for(int row = 0; row < net.get(i).length; row++) {
                    phold += (net.get(i)[row][col] + " ");
                }
                phold += "\n";
            }
            phold += "\n";
        }
        return phold;
    }

    public String getBiasStr() {
        String biasString = "";

        for(double[] i : bias) {
            for(double n : i) {
                biasString += n + " ";
            }
            biasString += "\n";
        }

        return biasString;
    }

    public String getNeuronsStr() {
        String phold = "";
        for(double[] i : neurons) {
            for(double n : i) {
                phold += (n + " ");
            }
            phold += "\n-\n";
        }

        return phold;
    }

    // SETTERS

    public void setInput(double[] input) {
        this.input = input;
    }
}
