package androidsamples.java.dicegames;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GamesViewModelWalletTest {
    private static final int INCR_VALUE = 5;
    private static final int WIN_VALUE = 6;

    @Spy
    private Die walletDie;
    @InjectMocks
    private GamesViewModel m = new GamesViewModel();
    private AutoCloseable closeable;

    @Before
    public void openMocks() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @After
    public void releaseMocks() throws Exception {
        closeable.close();
    }

    @Test
    public void rolling6IncrementsBalanceBy5() {
        int oldBalance = m.balance;
        when(walletDie.value()).thenReturn(WIN_VALUE);

        m.rollWalletDie();
        assertThat(m.balance, is(oldBalance + INCR_VALUE));
    }

    @Test
    public void rolling1DoesNotChangeBalance() {
        int oldBalance = m.balance;
        when(walletDie.value()).thenReturn(1);

        m.rollWalletDie();
        assertThat(m.balance, is(oldBalance));
    }

    @Test
    public void rolling6MultipleTimesIncrementsBalanceCorrectly() {
        int oldBalance = m.balance;
        when(walletDie.value()).thenReturn(WIN_VALUE);

        m.rollWalletDie();
        m.rollWalletDie();

        assertThat(m.balance, is(oldBalance + 2 * INCR_VALUE));
    }

    @Test
    public void rolling4AndThen6IncrementsBalanceCorrectly(){
        int oldBalance = m.balance;

        when(walletDie.value()).thenReturn(4);
        m.rollWalletDie();

        when(walletDie.value()).thenReturn(WIN_VALUE);
        m.rollWalletDie();

        assertThat(m.balance, is(oldBalance +  INCR_VALUE));
    }

    @Test
    public void rolling243DoesNotChangeBalance(){
        int oldBalance = m.balance;

        when(walletDie.value()).thenReturn(2);
        m.rollWalletDie();

        when(walletDie.value()).thenReturn(4);
        m.rollWalletDie();

        when(walletDie.value()).thenReturn(3);
        m.rollWalletDie();

        assertThat(m.balance, is(oldBalance));
    }

}