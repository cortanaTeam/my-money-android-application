package com.vn.hcmute.team.cortana.mymoney.di.module;

import android.content.Context;
import com.vn.hcmute.team.cortana.mymoney.data.DataRepository;
import com.vn.hcmute.team.cortana.mymoney.ui.transaction.TransactionPresenter;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.DebtLoanUseCase;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.ImageUseCase;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.TransactionUseCase;
import dagger.Module;
import dagger.Provides;

/**
 * Created by infamouSs on 9/28/17.
 */

@Module
public class TransactionModule {
    
    public TransactionModule() {
        
    }
    
    @Provides
    TransactionUseCase provideTransactionUseCase(Context context, DataRepository dataRepository) {
        return new TransactionUseCase(context.getApplicationContext(), dataRepository);
    }
    
    @Provides
    ImageUseCase provideImageUseCase(Context context, DataRepository dataRepository) {
        return new ImageUseCase(context, dataRepository);
    }
    
    @Provides
    DebtLoanUseCase provideDebtLoanUseCase(Context context, DataRepository dataRepository) {
        return new DebtLoanUseCase(context, dataRepository);
    }
    
    @Provides
    TransactionPresenter provideTransactionPresenter(TransactionUseCase transactionUseCase,
              ImageUseCase imageUseCase) {
        return new TransactionPresenter(transactionUseCase, imageUseCase);
    }
}
