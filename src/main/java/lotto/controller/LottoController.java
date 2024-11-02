package lotto.controller;

import lotto.service.LottoService;
import lotto.validation.LottoValidation;
import lotto.view.InputView;
import lotto.view.OutputView;

import java.util.List;

public class LottoController {

    private static final int LOTTO_PRICE = 1000;
    private final OutputView outputView;
    private final InputView inputView;
    private final LottoValidation lottoValidation;
    private final LottoService lottoService;

    public LottoController(OutputView outputView, InputView inputVIew, LottoValidation lottoValidation, LottoService lottoService) {
        this.outputView = outputView;
        this.inputView = inputVIew;
        this.lottoValidation = lottoValidation;
        this.lottoService = lottoService;
    }

    public void run() {
        outputView.askPurchasePrice();
        int purchasePrice = getValidPurchasePrice();

        int lottoCount = getLottoCount(purchasePrice);
        outputView.lottoPurchasedCount(lottoCount);
        List<List<Integer>> lottos = lottoService.buyLotto(lottoCount);
        printLottoPurchasedDetail(lottos);
    }

    private int getValidPurchasePrice() {
        while (true) {
            try {
                String input = inputView.inputPurchasePrice();
                lottoValidation.validateBlank(input);
                int purchasePrice = lottoValidation.validateParsing(input);
                lottoValidation.validatePositive(purchasePrice);
                lottoValidation.validateDivisible(purchasePrice);
                return purchasePrice;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private int getLottoCount(int purchasePrice) {
        return purchasePrice / LOTTO_PRICE;
    }

    private void printLottoPurchasedDetail(List<List<Integer>> lottos){
        for (List<Integer> lotto : lottos){
            outputView.lottoPurchasedDetail(lotto);
        }
    }
}
