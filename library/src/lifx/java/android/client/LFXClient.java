package lifx.java.android.client;

import android.content.Context;
import lifx.java.android.network_context.LFXNetworkContext;
import lifx.java.android.network_context.internal.transport_manager.lan.LFXLANTransportManager;
import lifx.java.internal.LFXWiFiObserver;
import lifx.java.internal.LFXWiFiObserver.LFXWiFiObserverCallback;

public class LFXClient
{
	private static LFXClient sharedInstance;
	private LFXNetworkContext localNetworkContext;
	
	public static LFXClient getSharedInstance( Context context)
	{
		if( sharedInstance == null)
		{
			sharedInstance = new LFXClient( context);
		}
		
		return sharedInstance;
	}

	private LFXClient( Context context)
	{
		super();
		
		LFXWiFiObserver.getSharedInstance( context).addObserverObjectWithCallback( this, new LFXWiFiObserverCallback()
		{
			@Override
			public void run( Object context, LFXWiFiObserver observer)
			{
				LFXClient client = (LFXClient) context;
				client.getLocalNetworkContext().resetAllCaches();
			}
		});
	}

	public LFXNetworkContext getLocalNetworkContext()
	{
		if( localNetworkContext == null)
		{
			localNetworkContext = LFXNetworkContext.initWithClientTransportManagerAndName( this, new LFXLANTransportManager(), "LAN");
		}
		
		return localNetworkContext;
	}
}