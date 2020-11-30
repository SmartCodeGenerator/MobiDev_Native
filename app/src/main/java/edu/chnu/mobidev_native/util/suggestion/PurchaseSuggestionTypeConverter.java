package edu.chnu.mobidev_native.util.suggestion;

import java.util.ArrayList;
import java.util.List;

import edu.chnu.mobidev_native.model.entity.suggestion.PurchaseSuggestion;
import edu.chnu.mobidev_native.model.entity.suggestion.PurchaseSuggestionDTO;
import edu.chnu.mobidev_native.model.entity.suggestion.PurchaseSuggestionData;

public interface PurchaseSuggestionTypeConverter {
    static List<PurchaseSuggestion> asDomainModelList(List<PurchaseSuggestionData> dataList) {
        ArrayList<PurchaseSuggestion> map = new ArrayList<>();

        for (PurchaseSuggestionData data : dataList) {
            map.add(new PurchaseSuggestion(data.getId(), data.getName(),
                    data.getPrice(), data.getImageBytes()));
        }

        return map;
    }

    static PurchaseSuggestionData[] asDataArray(List<PurchaseSuggestionDTO> dtoList) {
        PurchaseSuggestionData[] map = new PurchaseSuggestionData[dtoList.size()];

        for (int i = 0; i < dtoList.size(); i++) {
            PurchaseSuggestionDTO listItem = dtoList.get(i);

            map[i] = new PurchaseSuggestionData(listItem.getId(), listItem.getName(),
                    listItem.getPrice(), listItem.getImageBytes());
        }

        return map;
    }
}
