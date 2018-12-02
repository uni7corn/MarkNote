package me.shouheng.notepal.util.preferences;

import android.content.Context;
import android.text.TextUtils;

import java.util.LinkedList;
import java.util.List;

import me.shouheng.commons.utils.ColorUtils;
import me.shouheng.commons.utils.PalmUtils;
import me.shouheng.data.model.enums.FabSortItem;
import me.shouheng.data.model.enums.Operation;
import me.shouheng.notepal.PalmApp;
import me.shouheng.notepal.R;

/**
 * Created by WngShhng (shouheng2015@gmail.com) on 2018/4/9.
 */
public class PrefUtils extends BasePreferences {

    private static PrefUtils instance = new PrefUtils(PalmApp.getContext());

    public static List<FabSortItem> defaultFabOrders;

    public static PrefUtils getInstance() {
        return instance;
    }

    private final String ITEM_SORT_SPLIT = ":";

    static {
        defaultFabOrders = new LinkedList<>();
        defaultFabOrders.add(FabSortItem.NOTE);
        defaultFabOrders.add(FabSortItem.NOTEBOOK);
        defaultFabOrders.add(FabSortItem.QUICK_NOTE);
        defaultFabOrders.add(FabSortItem.CATEGORY);
        defaultFabOrders.add(FabSortItem.IMAGE);
        defaultFabOrders.add(FabSortItem.CAPTURE);
        defaultFabOrders.add(FabSortItem.DRAFT);
    }

    private PrefUtils(Context context) {
        super(context);
    }

    public List<FabSortItem> getFabSortResult() {
        String fabStr = getString(getKey(R.string.key_fab_sort_result), null);
        if (!TextUtils.isEmpty(fabStr)) {
            String[] fabs = fabStr.split(ITEM_SORT_SPLIT);
            List<FabSortItem> fabSortItems = new LinkedList<>();
            for (String fab : fabs) {
                fabSortItems.add(FabSortItem.valueOf(fab));
            }
            return fabSortItems;
        } else {
            return defaultFabOrders;
        }
    }

    public void setFabSortResult(List<FabSortItem> fabSortItems) {
        int size = fabSortItems.size();
        StringBuilder fabStr = new StringBuilder();
        for (int i=0; i<size; i++) {
            if (i == size - 1) {
                fabStr.append(fabSortItems.get(i).name());
            } else {
                fabStr.append(fabSortItems.get(i).name()).append(ITEM_SORT_SPLIT);
            }
        }
        putString(getKey(R.string.key_fab_sort_result), fabStr.toString());
    }

    public int getTimeLineColor(Operation operation) {
        return getInt(getKey(R.string.key_operation_color_prefix) + operation.name(), defaultTimeLineColor(operation));
    }

    private int defaultTimeLineColor(Operation operation) {
        switch (operation) {
            case DELETE: return PalmUtils.getColorCompact(R.color.md_red_500);
            case TRASH: return PalmUtils.getColorCompact(R.color.md_deep_orange_500);
            case ARCHIVE: return PalmUtils.getColorCompact(R.color.md_pink_500);
            case COMPLETE: return PalmUtils.getColorCompact(R.color.md_purple_500);
            case SYNCED: return PalmUtils.getColorCompact(R.color.md_light_green_900);
            case ADD: return PalmUtils.getColorCompact(R.color.md_green_500);
            case UPDATE: return PalmUtils.getColorCompact(R.color.md_light_green_700);
            case INCOMPLETE: return PalmUtils.getColorCompact(R.color.md_blue_500);
            case RECOVER: return PalmUtils.getColorCompact(R.color.md_light_blue_600);
        }
        return ColorUtils.accentColor(PalmApp.getContext());
    }
}
