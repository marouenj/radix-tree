package marouenj.tree.radix;

import marouenj.tree.radix.Radix;

import org.testng.Assert;
import org.testng.annotations.Test;

public class RadixTest {
	
	private static Radix<Integer> TREE;
	
	@Test
	public void exist_NullKey() {
		TREE = new Radix<>();
		Assert.assertEquals(TREE.exist(null), false);
	}
	
	@Test
	public void get_NullKey() {
		TREE = new Radix<>();
		Assert.assertEquals(TREE.get(null), null);
	}
	
	@Test
	public void get_NonExistingKey() {
		TREE = new Radix<>();
		Assert.assertEquals(TREE.get("abc"), null);
	}
	
	@Test
	public void get_NonExistingKey_ExistingPrefix() {
		TREE = new Radix<>();
		
		Assert.assertEquals(TREE.exist("abc123"), false);
		TREE.set("abc123", 1);
		Assert.assertEquals(TREE.exist("abc123"), true);
		Assert.assertEquals(TREE.get("abc123"), (Integer)1);

		Assert.assertEquals(TREE.get("abc"), null);
	}
	
	@Test
	public void set_NullKey() {
		TREE = new Radix<>();
		Assert.assertEquals(TREE.set(null, 1), false);
	}
	
	@Test
	public void set_KeyIsPrefixToExistingKey() {
		TREE = new Radix<>();
		
		// key not existing (empty tree)
		Assert.assertEquals(TREE.exist("insertion"), false);
		// key first insertion (empty tree)
		TREE.set("insertion", 1);
		Assert.assertEquals(TREE.exist("insertion"), true);
		Assert.assertEquals(TREE.get("insertion"), (Integer)1);
		// key update
		TREE.set("insertion", 2);
		Assert.assertEquals(TREE.exist("insertion"), true);
		Assert.assertEquals(TREE.get("insertion"), (Integer)2);

		// key not existing
		Assert.assertEquals(TREE.exist("insert"), false);
		// key first insertion
		TREE.set("insert", 3);
		Assert.assertEquals(TREE.exist("insertion"), true);
		Assert.assertEquals(TREE.exist("insert"), true);
		Assert.assertEquals(TREE.get("insertion"), (Integer)2);
		Assert.assertEquals(TREE.get("insert"), (Integer)3);
		// key update
		TREE.set("insert", 4);
		Assert.assertEquals(TREE.exist("insertion"), true);
		Assert.assertEquals(TREE.exist("insert"), true);
		Assert.assertEquals(TREE.get("insertion"), (Integer)2);
		Assert.assertEquals(TREE.get("insert"), (Integer)4);
	}
	
	@Test
	public void set_KeyHasPrefixAsExistingKey() {
		TREE = new Radix<>();
		
		// key not existing (empty tree)
		Assert.assertEquals(TREE.exist("insert"), false);
		// key first insertion (empty tree)
		TREE.set("insert", 1);
		Assert.assertEquals(TREE.exist("insert"), true);
		Assert.assertEquals(TREE.get("insert"), (Integer)1);
		// key update
		TREE.set("insert", 2);
		Assert.assertEquals(TREE.exist("insert"), true);
		Assert.assertEquals(TREE.get("insert"), (Integer)2);

		// key not existing
		Assert.assertEquals(TREE.exist("insertion"), false);
		// key first insertion
		TREE.set("insertion", 3);
		Assert.assertEquals(TREE.exist("insert"), true);
		Assert.assertEquals(TREE.exist("insertion"), true);
		Assert.assertEquals(TREE.get("insert"), (Integer)2);
		Assert.assertEquals(TREE.get("insertion"), (Integer)3);
		// key update
		TREE.set("insertion", 4);
		Assert.assertEquals(TREE.exist("insert"), true);
		Assert.assertEquals(TREE.exist("insertion"), true);
		Assert.assertEquals(TREE.get("insert"), (Integer)2);
		Assert.assertEquals(TREE.get("insertion"), (Integer)4);
	}
	
	@Test
	public void set_KeySharePrefixWithExistingKey() {
		TREE = new Radix<>();
		
		// key not existing (empty tree)
		Assert.assertEquals(TREE.exist("insert"), false);
		// key first insertion (empty tree)
		TREE.set("insert", 1);
		Assert.assertEquals(TREE.exist("insert"), true);
		Assert.assertEquals(TREE.get("insert"), (Integer)1);
		// key update
		TREE.set("insert", 2);
		Assert.assertEquals(TREE.exist("insert"), true);
		Assert.assertEquals(TREE.get("insert"), (Integer)2);

		// key not existing
		Assert.assertEquals(TREE.exist("inactive"), false);		
		// key first insertion
		TREE.set("inactive", 3);
		Assert.assertEquals(TREE.exist("in"), true);
		Assert.assertEquals(TREE.exist("insert"), true);
		Assert.assertEquals(TREE.exist("inactive"), true);
		Assert.assertEquals(TREE.get("in"), null);
		Assert.assertEquals(TREE.get("insert"), (Integer)2);
		Assert.assertEquals(TREE.get("inactive"), (Integer)3);
		// key update
		TREE.set("inactive", 4);
		Assert.assertEquals(TREE.exist("in"), true);
		Assert.assertEquals(TREE.exist("insert"), true);
		Assert.assertEquals(TREE.exist("inactive"), true);
		Assert.assertEquals(TREE.get("in"), null);
		Assert.assertEquals(TREE.get("insert"), (Integer)2);
		Assert.assertEquals(TREE.get("inactive"), (Integer)4);
	}
	
	@Test
	public void set_KeyNotExist_PrefixExist() {
		TREE = new Radix<>();
		
		Assert.assertEquals(TREE.exist("abc123"), false);
		TREE.set("abc123", 1);
		Assert.assertEquals(TREE.exist("abc123"), true);
		Assert.assertEquals(TREE.get("abc123"), (Integer)1);

		Assert.assertEquals(TREE.exist("abcxyz"), false);
		TREE.set("abcxyz", 2);
		Assert.assertEquals(TREE.exist("abc"), true);
		Assert.assertEquals(TREE.exist("abc123"), true);
		Assert.assertEquals(TREE.exist("abcxyz"), true);
		Assert.assertEquals(TREE.get("abc"), null);
		Assert.assertEquals(TREE.get("abc123"), (Integer)1);
		Assert.assertEquals(TREE.get("abcxyz"), (Integer)2);
		
		// key 'abc' has no value at this point
		
		TREE.set("abc", 3);
		Assert.assertEquals(TREE.exist("abc"), true);
		Assert.assertEquals(TREE.exist("abc123"), true);
		Assert.assertEquals(TREE.exist("abcxyz"), true);
		Assert.assertEquals(TREE.get("abc"), (Integer)3);
		Assert.assertEquals(TREE.get("abc123"), (Integer)1);
		Assert.assertEquals(TREE.get("abcxyz"), (Integer)2);

	}
	
	@Test
	public void del_TheOnlyExistingKey() {
		TREE = new Radix<>();
		
		TREE.set("abc", 1);
		Assert.assertEquals(TREE.exist("abc"), true);
		Assert.assertEquals(TREE.get("abc"), (Integer)1);
		
		TREE.del("abc");
		Assert.assertEquals(TREE.exist("abc"), false);
	}
	
	@Test
	public void del_KeyHasPrefixAsAnotherKey() {
		TREE = new Radix<>();
		
		TREE.set("abc", 100);
		Assert.assertEquals(TREE.exist("abc"), true);
		Assert.assertEquals(TREE.get("abc"), (Integer)100);
		
		TREE.set("abc123", 200);
		Assert.assertEquals(TREE.exist("abc123"), true);
		Assert.assertEquals(TREE.get("abc123"), (Integer)200);
		
		TREE.del("abc123");
		Assert.assertEquals(TREE.exist("abc"), true);
		Assert.assertEquals(TREE.exist("abc123"), false);
		Assert.assertEquals(TREE.get("abc"), (Integer)100);
		Assert.assertEquals(TREE.get("abc123"), null);
	}
	
	@Test
	public void del_KeyIsPrefixToAnotherKey() {
		TREE = new Radix<>();
		
		TREE.set("abc", 100);
		Assert.assertEquals(TREE.exist("abc"), true);
		Assert.assertEquals(TREE.get("abc"), (Integer)100);
		
		TREE.set("abc123", 200);
		Assert.assertEquals(TREE.exist("abc123"), true);
		Assert.assertEquals(TREE.get("abc123"), (Integer)200);
		
		TREE.del("abc");
		Assert.assertEquals(TREE.exist("abc"), true);
		Assert.assertEquals(TREE.exist("abc123"), true);
		Assert.assertEquals(TREE.get("abc"), null);
		Assert.assertEquals(TREE.get("abc123"), (Integer)200);
	}
	
	@Test
	public void del_SingleSiblingShouldJoinParentIfParentHasNoValue() {
		TREE = new Radix<>();
		
		TREE.set("inactive", 100);
		Assert.assertEquals(TREE.exist("inactive"), true);
		Assert.assertEquals(TREE.get("inactive"), (Integer)100);
		
		TREE.set("insertion", 200);
		Assert.assertEquals(TREE.exist("in"), true);
		Assert.assertEquals(TREE.get("in"), null);
		Assert.assertEquals(TREE.exist("inactive"), true);
		Assert.assertEquals(TREE.get("inactive"), (Integer)100);
		Assert.assertEquals(TREE.exist("insertion"), true);
		Assert.assertEquals(TREE.get("insertion"), (Integer)200);
		
		TREE.set("inserting", 300);
		Assert.assertEquals(TREE.exist("in"), true);
		Assert.assertEquals(TREE.get("in"), null);
		Assert.assertEquals(TREE.exist("inactive"), true);
		Assert.assertEquals(TREE.get("inactive"), (Integer)100);
		Assert.assertEquals(TREE.exist("inserti"), true);
		Assert.assertEquals(TREE.get("inserti"), null);
		Assert.assertEquals(TREE.exist("insertion"), true);
		Assert.assertEquals(TREE.get("insertion"), (Integer)200);
		Assert.assertEquals(TREE.exist("inserting"), true);
		Assert.assertEquals(TREE.get("inserting"), (Integer)300);
		
		TREE.set("insertions", 400);
		Assert.assertEquals(TREE.exist("in"), true);
		Assert.assertEquals(TREE.get("in"), null);
		Assert.assertEquals(TREE.exist("inactive"), true);
		Assert.assertEquals(TREE.get("inactive"), (Integer)100);
		Assert.assertEquals(TREE.exist("inserti"), true);
		Assert.assertEquals(TREE.get("inserti"), null);
		Assert.assertEquals(TREE.exist("insertion"), true);
		Assert.assertEquals(TREE.get("insertion"), (Integer)200);
		Assert.assertEquals(TREE.exist("inserting"), true);
		Assert.assertEquals(TREE.get("inserting"), (Integer)300);
		Assert.assertEquals(TREE.exist("insertions"), true);
		Assert.assertEquals(TREE.get("insertions"), (Integer)400);
		
		TREE.del("inserting");
		Assert.assertEquals(TREE.exist("in"), true);
		Assert.assertEquals(TREE.get("in"), null);
		Assert.assertEquals(TREE.exist("inactive"), true);
		Assert.assertEquals(TREE.get("inactive"), (Integer)100);
		Assert.assertEquals(TREE.exist("inserti"), false);
		Assert.assertEquals(TREE.exist("insertion"), true);
		Assert.assertEquals(TREE.get("insertion"), (Integer)200);
		Assert.assertEquals(TREE.exist("inserting"), false);
		Assert.assertEquals(TREE.exist("insertions"), true);
		Assert.assertEquals(TREE.get("insertions"), (Integer)400);
	}
	
	@Test
	public void del_SingleSiblingShouldNotJoinParentIfParentHasValue() {
		TREE = new Radix<>();
		
		TREE.set("inactive", 100);
		Assert.assertEquals(TREE.exist("inactive"), true);
		Assert.assertEquals(TREE.get("inactive"), (Integer)100);
		
		TREE.set("insertion", 200);
		Assert.assertEquals(TREE.exist("in"), true);
		Assert.assertEquals(TREE.get("in"), null);
		Assert.assertEquals(TREE.exist("inactive"), true);
		Assert.assertEquals(TREE.get("inactive"), (Integer)100);
		Assert.assertEquals(TREE.exist("insertion"), true);
		Assert.assertEquals(TREE.get("insertion"), (Integer)200);
		
		TREE.set("inserting", 300);
		Assert.assertEquals(TREE.exist("in"), true);
		Assert.assertEquals(TREE.get("in"), null);
		Assert.assertEquals(TREE.exist("inactive"), true);
		Assert.assertEquals(TREE.get("inactive"), (Integer)100);
		Assert.assertEquals(TREE.exist("inserti"), true);
		Assert.assertEquals(TREE.get("inserti"), null);
		Assert.assertEquals(TREE.exist("insertion"), true);
		Assert.assertEquals(TREE.get("insertion"), (Integer)200);
		Assert.assertEquals(TREE.exist("inserting"), true);
		Assert.assertEquals(TREE.get("inserting"), (Integer)300);
		
		TREE.set("insertions", 400);
		Assert.assertEquals(TREE.exist("in"), true);
		Assert.assertEquals(TREE.get("in"), null);
		Assert.assertEquals(TREE.exist("inactive"), true);
		Assert.assertEquals(TREE.get("inactive"), (Integer)100);
		Assert.assertEquals(TREE.exist("inserti"), true);
		Assert.assertEquals(TREE.get("inserti"), null);
		Assert.assertEquals(TREE.exist("insertion"), true);
		Assert.assertEquals(TREE.get("insertion"), (Integer)200);
		Assert.assertEquals(TREE.exist("inserting"), true);
		Assert.assertEquals(TREE.get("inserting"), (Integer)300);
		Assert.assertEquals(TREE.exist("insertions"), true);
		Assert.assertEquals(TREE.get("insertions"), (Integer)400);
		
		TREE.set("inserti", 500);
		Assert.assertEquals(TREE.exist("in"), true);
		Assert.assertEquals(TREE.get("in"), null);
		Assert.assertEquals(TREE.exist("inactive"), true);
		Assert.assertEquals(TREE.get("inactive"), (Integer)100);
		Assert.assertEquals(TREE.exist("insertion"), true);
		Assert.assertEquals(TREE.get("insertion"), (Integer)200);
		Assert.assertEquals(TREE.exist("inserting"), true);
		Assert.assertEquals(TREE.get("inserting"), (Integer)300);
		Assert.assertEquals(TREE.exist("insertions"), true);
		Assert.assertEquals(TREE.get("insertions"), (Integer)400);
		Assert.assertEquals(TREE.exist("inserti"), true);
		Assert.assertEquals(TREE.get("inserti"), (Integer)500);
		
		TREE.del("inserting");
		Assert.assertEquals(TREE.exist("in"), true);
		Assert.assertEquals(TREE.get("in"), null);
		Assert.assertEquals(TREE.exist("inactive"), true);
		Assert.assertEquals(TREE.get("inactive"), (Integer)100);
		Assert.assertEquals(TREE.exist("inserti"), true);
		Assert.assertEquals(TREE.get("inserti"), (Integer)500);
		Assert.assertEquals(TREE.exist("insertion"), true);
		Assert.assertEquals(TREE.get("insertion"), (Integer)200);
		Assert.assertEquals(TREE.exist("inserting"), false);
		Assert.assertEquals(TREE.exist("insertions"), true);
		Assert.assertEquals(TREE.get("insertions"), (Integer)400);
	}
}
