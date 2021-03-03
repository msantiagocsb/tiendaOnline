package es.urjc.computadores;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class CacheController {
	
	@Autowired
	private CacheManager cacheManager;
	
	/*@RequestMapping(value="/cache", method=RequestMethod.GET)
	public Map<Object, Object> getCacheContent() {
		ConcurrentMapCacheManager cacheMgr = (ConcurrentMapCacheManager) cacheManager;
		ConcurrentMapCache cache = (ConcurrentMapCache) cacheMgr.getCache("cache_marketplace");
		System.out.println("Ha entrado");
		return cache.getNativeCache();
	}*/
	@RequestMapping("/cache")
	public String getCacheProductos(Model model){
		ConcurrentMapCacheManager cacheMan=(ConcurrentMapCacheManager) cacheManager;
		ConcurrentMapCache cache=(ConcurrentMapCache) cacheMan.getCache("cache_marketplace");
		model.addAttribute("cache", cache.getNativeCache().toString());
		return "cache";
	}
	
	
}