public class pa02 {
    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.out.println("Usage: java pa02 input_file checksum_size");
            return;
        }

        String inputFile = args[0];
        int checksumSize = Integer.parseInt(args[1]); // checksum size in bits

        // Read the input file
        // echo processed input to screen
        System.out.println(inputFile);
    }
}
