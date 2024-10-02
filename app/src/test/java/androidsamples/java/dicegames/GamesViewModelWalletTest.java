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
        when(walletDie.value()).thenReturn(WIN_VALUE);
        m.setDie(walletDie);
        int oldBalance = m.balance;

        m.rollWalletDie();
        assertThat(m.balance, is(oldBalance + INCR_VALUE));
    }

    @Test
    public void rolling1DoesNotChangeBalance() {
        when(walletDie.value()).thenReturn(1);
        m.setDie(walletDie);
        int oldBalance = m.balance;

        m.rollWalletDie();
        assertThat(m.balance, is(oldBalance));
    }

    @Test
    public void rolling6MultipleTimesIncrementsBalanceCorrectly() {
        when(walletDie.value()).thenReturn(WIN_VALUE);
        m.setDie(walletDie);
        int oldBalance = m.balance;

        m.rollWalletDie();
        m.rollWalletDie();

        assertThat(m.balance, is(oldBalance + 2 * INCR_VALUE));
    }

    @Test
    public void rolling4AndThen6IncrementsBalanceCorrectly(){
        when(walletDie.value()).thenReturn(4);
        m.setDie(walletDie);
        int oldBalance = m.balance;
        m.rollWalletDie();

        when(walletDie.value()).thenReturn(WIN_VALUE);
        m.rollWalletDie();

        assertThat(m.balance, is(oldBalance +  INCR_VALUE));
    }

    @Test
    public void rolling243DoesNotChangeBalance(){
        int oldBalance = m.balance;
        m.setDie(walletDie);

        when(walletDie.value()).thenReturn(2);
        m.rollWalletDie();

        when(walletDie.value()).thenReturn(4);
        m.rollWalletDie();

        when(walletDie.value()).thenReturn(3);
        m.rollWalletDie();

        assertThat(m.balance, is(oldBalance));
    }

}