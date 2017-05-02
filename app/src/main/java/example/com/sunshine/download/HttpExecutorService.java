package example.com.sunshine.download;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


class HttpExecutorService extends ThreadPoolExecutor {
  private static final int DEFAULT_THREAD_COUNT = 3;

  HttpExecutorService() {
    super(DEFAULT_THREAD_COUNT, DEFAULT_THREAD_COUNT, 0, TimeUnit.MILLISECONDS,
            new PriorityBlockingQueue<Runnable>(), new Utlis.PicassoThreadFactory());
  }

  void adjustThreadCount(NetworkInfo info) {
    if (info == null || !info.isConnectedOrConnecting()) {
      setThreadCount(DEFAULT_THREAD_COUNT);
      return;
    }
    switch (info.getType()) {
      case ConnectivityManager.TYPE_WIFI:
      case ConnectivityManager.TYPE_WIMAX:
      case ConnectivityManager.TYPE_ETHERNET:
        setThreadCount(3);
        break;
      case ConnectivityManager.TYPE_MOBILE:
        switch (info.getSubtype()) {
          case TelephonyManager.NETWORK_TYPE_LTE:  // 4G
          case TelephonyManager.NETWORK_TYPE_HSPAP:
          case TelephonyManager.NETWORK_TYPE_EHRPD:
            setThreadCount(2);
            break;
          case TelephonyManager.NETWORK_TYPE_UMTS: // 3G
          case TelephonyManager.NETWORK_TYPE_CDMA:
          case TelephonyManager.NETWORK_TYPE_EVDO_0:
          case TelephonyManager.NETWORK_TYPE_EVDO_A:
          case TelephonyManager.NETWORK_TYPE_EVDO_B:
            setThreadCount(2);
            break;
          case TelephonyManager.NETWORK_TYPE_GPRS: // 2G
          case TelephonyManager.NETWORK_TYPE_EDGE:
            setThreadCount(1);
            break;
          default:
            setThreadCount(DEFAULT_THREAD_COUNT);
        }
        break;
      default:
        setThreadCount(DEFAULT_THREAD_COUNT);
    }
  }@Override
  public Future<?> submit(Runnable task) {
    PicassoFutureTask ftask = new PicassoFutureTask((HttpDownHunter) task);
    execute(ftask);
    return ftask;
  }

  private void setThreadCount(int threadCount) {
    setCorePoolSize(threadCount);
    setMaximumPoolSize(threadCount);
      }

  private static final class PicassoFutureTask extends FutureTask<HttpDownHunter>
          implements Comparable<PicassoFutureTask> {
    private final HttpDownHunter hunter;

    public PicassoFutureTask(HttpDownHunter hunter) {
      super(hunter, null);
      this.hunter = hunter;
    }

    @Override
    public int compareTo(PicassoFutureTask other) {
      // High-priority requests are "lesser" so they are sorted to the front.
      // Equal priorities are sorted by sequence number to provide FIFO ordering.
      return (hunter.sequence - other.hunter.sequence);
    }
  }
}