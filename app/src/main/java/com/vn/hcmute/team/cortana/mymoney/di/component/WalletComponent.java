package com.vn.hcmute.team.cortana.mymoney.di.component;

import com.vn.hcmute.team.cortana.mymoney.di.module.ActivityModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.WalletModule;
import com.vn.hcmute.team.cortana.mymoney.di.scope.PerActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.main.MainActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.wallet.AddWalletActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.wallet.EditWalletActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.wallet.MyWalletActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.wallet.SelectWalletActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.wallet.TransferMoneyActivity;
import dagger.Component;

/**
 * Created by kunsubin on 8/22/2017.
 */
@PerActivity
@Component(modules = {ActivityModule.class,
          WalletModule.class}, dependencies = ApplicationComponent.class)
public interface WalletComponent {
    
    void inject(MainActivity mainActivity);
    
    void inject(SelectWalletActivity selectWalletActivity);
    
    void inject(AddWalletActivity addWalletActivity);
    
    void inject(EditWalletActivity editWalletActivity);
    
    void inject(MyWalletActivity myWalletActivity);
    
    void inject(TransferMoneyActivity transferMoneyActivity);
}
